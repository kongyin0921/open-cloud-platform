package com.ocp.common.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author kong
 * @date 2021/08/08 12:18
 * blog: http://blog.kongyin.ltd
 */
@Data
@ConfigurationProperties(prefix = "ocp.audit-log")
@RefreshScope
public class AuditLogProperties {
    /**
     * 是否开启审计日志
     */
    private Boolean enabled = false;
    /**
     * 日志记录类型(logger/redis/db/es)
     */
    private String logType;
}
