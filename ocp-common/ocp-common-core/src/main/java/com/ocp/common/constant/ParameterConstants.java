package com.ocp.common.constant;

/**
 * 参数常量
 * @author kong
 * @date 2021/08/14 11:36
 * blog: http://blog.kongyin.ltd
 */
public interface ParameterConstants {

    /**
     * The access token issued by the authorization server. This value is REQUIRED.
     */
    String ACCESS_TOKEN = "access_token";

    /**
     * 租户id参数
     */
    String TENANT_ID_PARAM = "tenantId";

    /**
     * 日志链路追踪id日志标志
     */
    String LOG_TRACE_ID = "traceId";
}
