package com.ocp.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 负载均衡策略Holder
 * @author kong
 * @date 2021/08/04 22:34
 * blog: http://blog.kongyin.ltd
 */
public class LbIsolationContextHolder {
    private static final ThreadLocal<String> VERSION_CONTEXT = new TransmittableThreadLocal<>();

    public static void setVersion(String version) {
        VERSION_CONTEXT.set(version);
    }

    public static String getVersion() {
        return VERSION_CONTEXT.get();
    }

    public static void clear() {
        VERSION_CONTEXT.remove();
    }
}
