package com.ocp.common.log.service;

import com.ocp.common.log.model.Audit;

/**
 * 审计日志接口
 * @author kong
 * @date 2021/08/08 12:30
 * blog: http://blog.kongyin.ltd
 */
public interface IAuditService {
    void save(Audit audit);
}
