package com.cloud.photo.trans.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.trans.entity.FileMd5;
import com.cloud.photo.trans.entity.StorageObject;
import com.cloud.photo.trans.service.IFileMd5Service;
import com.cloud.photo.trans.service.IPutuploadService;
import com.cloud.photo.trans.service.IStorageObjectService;
import com.cloud.photo.trans.service.IUserFileService;
import com.cloud.photo.trans.util.S3UtilMinio;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PutuploadServiceImpl implements IPutuploadService {

    @Autowired
    private IFileMd5Service iFileMd5Service;

    @Autowired
    private IStorageObjectService iStorageObjectService;

    @Autowired
    private IUserFileService iUserFileService;

    @Override
    public String getPutUploadUrl(String fileName, String fileMd5, Long fileSize) {
        FileMd5 fileMd5Entity = iFileMd5Service.getOne(new QueryWrapper<FileMd5>().eq("md5", fileMd5));
        //文件已存在 进行秒传
        if (fileMd5Entity != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("storageobjectId", fileMd5Entity.getStorageObjectId());
            return jsonObject.toJSONString();
        }

        //文件不存在
        String suffixName = "";
        if (StringUtils.isNotBlank(fileName)) {
            suffixName = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        }
        return S3UtilMinio.getPutUploadUrl(suffixName, fileMd5);
    }

    @Override
    public CommonEnum commitTransSecond(FileUploadBo bo){
        //校验存储ID 是否正确
        StorageObject storageObject = iStorageObjectService.getById(bo.getStorageObjectId());
        if(storageObject == null){
            return CommonEnum.FILE_UPLOADED_ERROR;
        }
        //检查秒传文件大小
        if(!storageObject.getObjectSize().equals(bo.getFileSize())
                || !StringUtils.equalsIgnoreCase(bo.getFileMd5(), storageObject.getMd5()) ){
            return CommonEnum.FILE_UPLOADED_ERROR;
        }

        //保存文件入库- 用户文件列表    -发送到审核、图片kafka列表
        boolean result = iUserFileService.saveAndFileDeal(bo);

        if(result){
            return CommonEnum.SUCCESS;
        }else {
            return CommonEnum.FILE_UPLOADED_ERROR;
        }
    }

    @Override
    public CommonEnum commit(FileUploadBo bo){
        //获取文件资源池存情信息
        S3ObjectSummary s3ObjectSummary = S3UtilMinio.getObjectInfo(bo.getObjectId());
        //文件未上传
        if(s3ObjectSummary == null) {
            return CommonEnum.FILE_NOT_UPLOADED;
        }

        //文件上传错误
        if(!bo.getFileSize().equals(s3ObjectSummary.getSize())
                || !StringUtils.equalsIgnoreCase(s3ObjectSummary.getETag(),bo.getFileMd5())){
            return CommonEnum.FILE_UPLOADED_ERROR;
        }

        //文件上传成功 -文件存储信息入库
        StorageObject storageObject =new StorageObject("minio",bo.getContainerId(), bo.getObjectId(),
                bo.getFileMd5(), bo.getFileSize());
        iStorageObjectService.save(storageObject);

        //文件MD5入库，秒传用
        FileMd5 fileMd5Bo = new FileMd5(bo.getFileMd5() ,bo.getFileSize() ,storageObject.getStorageObjectId());
        iFileMd5Service.save(fileMd5Bo);
        //文件入库 - 用户文件列表 - 发送到审核、图片 kafka列表
        bo.setStorageObjectId(storageObject.getStorageObjectId());
        iUserFileService.saveAndFileDeal(bo);

        return CommonEnum.SUCCESS;
    }
}