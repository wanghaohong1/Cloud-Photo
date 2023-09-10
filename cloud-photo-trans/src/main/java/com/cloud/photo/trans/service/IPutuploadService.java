package com.cloud.photo.trans.service;

import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.common.common.ResultBody;

public interface IPutuploadService {
    String getPutUploadUrl(String fileName , String fileMd5 ,Long filSize);

    CommonEnum commit(FileUploadBo bo);

    CommonEnum commitTransSecond(FileUploadBo bo);
}
