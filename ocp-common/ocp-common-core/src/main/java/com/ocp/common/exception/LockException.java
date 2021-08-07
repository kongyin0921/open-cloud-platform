package com.ocp.common.exception;

/**
 * 分布式锁异常
 * @author kong
 * @date 2021/07/18 21:06
 * blog: http://blog.kongyin.ltd
 */
public class LockException extends RuntimeException{
    private static final long serialVersionUID = 6610083281801529147L;

    public LockException() {
        super();
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockException(Throwable cause) {
        super(cause);
    }

    public LockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
