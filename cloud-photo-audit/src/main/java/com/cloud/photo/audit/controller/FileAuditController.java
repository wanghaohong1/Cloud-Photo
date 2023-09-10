package com.cloud.photo.audit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.photo.audit.entity.FileAudit;
import com.cloud.photo.audit.service.IFileAuditService;
import com.cloud.photo.common.bo.AuditPageBo;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.constant.CommonConstant;
import com.cloud.photo.common.feign.CloudPhotoTransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/audit")
public class FileAuditController {

    @Autowired
    IFileAuditService iFileAuditService;
    @Autowired
    CloudPhotoTransService cloudPhotoTransService;

    /**
     * 审核列表
     * @param pageBo
     * @return
     */
    @PostMapping("/getAuditList")
    public ResultBody getAuditList(@RequestBody AuditPageBo pageBo){
        //1.设置查询Mapper
        QueryWrapper<FileAudit> qw = new QueryWrapper<FileAudit>();

        if(pageBo.getAuditStatusList() != null && pageBo.getAuditStatusList().size()>0){
            qw.in("audit_status",pageBo.getAuditStatusList());
        }else{
            qw.eq("audit_status", CommonConstant.FILE_AUDIT);
        }
        Integer current = pageBo.getCurrent();
        Integer pageSize = pageBo.getPageSize();
        if(current == null){
            current = 1;
        }
        if(pageSize == null){
            pageSize = 20;
        }
        Page<FileAudit> page = new Page<FileAudit>(current, pageSize);
        IPage<FileAudit> userFileIPage = iFileAuditService.page(page,qw.orderByAsc("create_time"));
        return ResultBody.success(userFileIPage);
    }

    @PostMapping("/updateAuditStatus")
    public ResultBody updateAuditStatus(@RequestBody AuditPageBo pageBo){
        Boolean result = iFileAuditService.updateAuditStatus(pageBo);
        return  ResultBody.success(result);
    }


//    @PostMapping("/getAuditOkList")
//    public ResultBody getAuditOkList(@RequestBody AuditPageBo pageBo){
//        //1.设置查询Mapper
//        QueryWrapper<FileAudit> qw = new QueryWrapper<FileAudit>();
//
//        if(pageBo.getAuditStatusList() != null && pageBo.getAuditStatusList().size()>0){
//            qw.eq("audit_status", CommonConstant.FILE_AUDIT_ACCESS);
//            qw.eq("audit_status", CommonConstant.FILE_AUDIT_FAIL);
//        }
//        Integer current = pageBo.getCurrent();
//        Integer pageSize = pageBo.getPageSize();
//        if(current == null){
//            current = 1;
//        }
//        if(pageSize == null){
//            pageSize = 20;
//        }
//        Page<FileAudit> page = new Page<FileAudit>(current, pageSize);
//        IPage<FileAudit> userFileIPage = iFileAuditService.page(page,qw.orderByAsc("create_time"));
//        return ResultBody.success(userFileIPage);
//    }

}
