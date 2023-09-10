package com.cloud.photo.audit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.photo.audit.entity.FileAudit;
import com.cloud.photo.common.bo.AuditPageBo;

/**
 * <p>
 * 文件审核列表 服务类
 * </p>
 *
 * @author wfc
 * @since 2023-07-14
 */
public interface IFileAuditService extends IService<FileAudit> {
    Boolean updateAuditStatus(AuditPageBo pageBo);
}
