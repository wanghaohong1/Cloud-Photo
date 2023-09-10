package com.cloud.photo.trans.controller;

import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.trans.service.IDownloadService;
import com.cloud.photo.trans.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/trans")
@Slf4j
public class DownloadController {
    @Autowired
    IDownloadService iDownloadService;

    /**
     * 获取下载地址--通过文件id
     * @param request
     * @param response
     * @param userId
     * @param fileId
     * @return
     */
    @RequestMapping("/getDownloadUrlByFileId")
    public ResultBody getDownloadUrlByFileId(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam String userId, @RequestParam String fileId){
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request);
        String url = iDownloadService.getDownloadUrlByFileId(userId, fileId);
        log.info("getPutUploadUrl() userId = " + userId + ", url = " + url);
        return ResultBody.success(url, requestId);
    }

    /**
     * 获取下载地址--通过资源池id，资源池objectid
     * @param request
     * @param response
     * @param containerId
     * @param objectId
     * @return
     */
    @RequestMapping("/getDownloadUrl")
    public ResultBody getDownloadUrl(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam String containerId, @RequestParam String objectId){
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request);
        String url = iDownloadService.getDownloadUrl(containerId, objectId);
        log.info("getPutUploadUrl() url = " + url);
        return ResultBody.success(url);
    }
}
