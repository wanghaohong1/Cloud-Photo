package com.cloud.photo.trans.service.impl;

import com.cloud.photo.trans.entity.StorageObject;
import com.cloud.photo.trans.entity.UserFile;
import com.cloud.photo.trans.service.IDownloadService;
import com.cloud.photo.trans.service.IFileMd5Service;
import com.cloud.photo.trans.service.IStorageObjectService;
import com.cloud.photo.trans.service.IUserFileService;
import com.cloud.photo.trans.util.S3UtilMinio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DownloadServiceImpl implements IDownloadService {

    @Autowired
    IStorageObjectService iStorageObjectService;

    @Autowired
    IUserFileService iUserFileService;

    @Autowired
    IFileMd5Service iFileMd5Service;

    @Override
    public String getDownloadUrlByFileId(String userId, String fileId) {

        //查询文件信息
        UserFile userFile = iUserFileService.getById(fileId);
        if(userFile == null){
            log.error("getDownloadUrlByFileId() userFile is null, fileId = " + fileId);
            return null;
        }

        //查询文件存储信息
        StorageObject storageObject = iStorageObjectService.getById(userFile.getStorageObjectId());
        if(storageObject == null){
            log.error("getDownloadUrlByFileId() storageObject is null, fileId = " + fileId);
            return null;
        }

        //生成地址下载地址
        return S3UtilMinio.getDownloadUrl(storageObject.getContainerId(), storageObject.getObjectId(), userFile.getFileName());
    }

    @Override
    public String getDownloadUrl(String containerId, String objectId) {
        return S3UtilMinio.getDownloadUrl(containerId,objectId);
    }
}
