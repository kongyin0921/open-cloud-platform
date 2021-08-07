package com.ocp.common.exception;

/**
 * @author kong
 * @date 2021/08/07 19:40
 * blog: http://blog.kongyin.ltd
 */
public class IdempotencyException extends RuntimeException {

    public IdempotencyException() {
        super();
    }

    public IdempotencyException(String message) {
        super(message);
    }

    public IdempotencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdempotencyException(Throwable cause) {
        super(cause);
    }

    public IdempotencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
