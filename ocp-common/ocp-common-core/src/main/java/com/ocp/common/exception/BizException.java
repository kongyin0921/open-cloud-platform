package com.ocp.common.exception;

/**
 * @author kong
 * @date 2021/08/07 19:44
 * blog: http://blog.kongyin.ltd
 */
public class BizException extends AbstractBizException {
    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
