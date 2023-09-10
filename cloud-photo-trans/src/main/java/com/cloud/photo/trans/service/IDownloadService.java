package com.cloud.photo.trans.service;

public interface IDownloadService {
    String getDownloadUrlByFileId(String userId, String fileId);

    String getDownloadUrl(String containerId, String objectId);
}
