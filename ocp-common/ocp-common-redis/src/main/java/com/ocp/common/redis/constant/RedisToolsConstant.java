package com.ocp.common.redis.constant;

/**
 * redis 工具常量
 * @author kong
 * @date 2021/07/19 20:49
 * blog: http://blog.kongyin.ltd
 */
public class RedisToolsConstant {
    private RedisToolsConstant() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * single Redis
     */
    public final static int SINGLE = 1 ;

    /**
     * Redis cluster
     */
    public final static int CLUSTER = 2 ;
}
