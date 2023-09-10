package com.cloud.photo.trans.service;

import com.cloud.photo.common.bo.FileUploadBo;
import com.cloud.photo.trans.entity.UserFile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whh
 * @since 2023-07-13
 */
public interface IUserFileService extends IService<UserFile> {
    public boolean saveAndFileDeal(FileUploadBo bo);
}
