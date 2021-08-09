package com.ocp.common.constant;

/**
 * 信息头常量
 * @author kong
 * @date 2021/08/08 21:43
 * blog: http://blog.kongyin.ltd
 */
public interface MessageHeaderConstants {

    /**
     * 用户信息头
     */
    String USER_HEADER = "x-user-header";

    /**
     * 用户id信息头
     */
    String USER_ID_HEADER = "x-user-id-header";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";

    /**
     * 租户信息头(应用)
     */
    String TENANT_HEADER = "x-tenant-header";

    /**
     * 账号类型信息头
     */
    String ACCOUNT_TYPE_HEADER = "x-account-type-header";

    /**
     * 日志链路追踪id信息头
     */
    String TRACE_ID_HEADER = "x-traceId-header";

    /**
     * 负载均衡策略-版本号 信息头
     */
    String O_C_P_VERSION = "o-c-p-version";
}
