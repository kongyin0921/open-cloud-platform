package com.ocp.common.log.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审计日志
 * @author kong
 * @date 2021/08/08 12:17
 * blog: http://blog.kongyin.ltd
 */
@Data
public class Audit {
    /**
     * 操作时间
     */
    private LocalDateTime timestamp;
    /**
     * 应用名
     */
    private String applicationName;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 租户id
     */
    private String clientId;
    /**
     * 操作信息
     */
    private String operation;
}
