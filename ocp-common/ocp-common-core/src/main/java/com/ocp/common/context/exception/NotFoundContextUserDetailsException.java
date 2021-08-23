package com.ocp.common.context.exception;


import com.ocp.common.exception.AbstractBizException;

/**
 * 未找到当前登录用户信息
 *
 * @author kong
 **/
public class NotFoundContextUserDetailsException extends AbstractBizException {

    public NotFoundContextUserDetailsException() {
        super();
    }

    public NotFoundContextUserDetailsException(String message) {
        super(message);
    }

    public NotFoundContextUserDetailsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundContextUserDetailsException(Throwable cause) {
        super(cause);
    }

    public NotFoundContextUserDetailsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
