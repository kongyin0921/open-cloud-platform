package com.ocp.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.ocp.common.context.exception.NotFoundContextUserDetailsException;

/**
 * 用户上下文对象
 * @author kong
 * @date 2021/08/22 15:32
 * blog: http://blog.kongyin.ltd
 */
public class UserDetailsContextHolder {
    /**
     * 用户信息
     */
    private static final ThreadLocal<ContextUserDetails> userDetailsThreadLocal = new TransmittableThreadLocal<>();

    /**
     * 设置当前上下文用户信息
     *
     * @param userDetails 用户信息
     */
    public static void setContextUserDetails(ContextUserDetails userDetails) {
        userDetailsThreadLocal.set(userDetails);
    }


    /**
     * 获取当前用户信息
     *
     * @param ignoreNull 忽略用户信息不存在情况。取值后注意处理空值情况
     * @return 用户信息
     * @throws NotFoundContextUserDetailsException 用户未找到异常
     */
    public static ContextUserDetails getContextUserDetails(boolean ignoreNull) throws NotFoundContextUserDetailsException {
        ContextUserDetails contextUserDetails = userDetailsThreadLocal.get();
        if (!ignoreNull && contextUserDetails == null) {
            throw new NotFoundContextUserDetailsException("无法获取用户信息，需要身份认证信息。");
        }
        return contextUserDetails;
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     * @throws NotFoundContextUserDetailsException 用户未找到异常
     */
    public static ContextUserDetails getContextUserDetails() throws NotFoundContextUserDetailsException {
        return getContextUserDetails(false);
    }

    /**
     * 清除上下文用户信息
     */
    public static void cleanContextUserDetails() {
        userDetailsThreadLocal.remove();
    }
}
