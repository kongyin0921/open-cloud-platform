package com.ocp.common.exception;

/**
 * 业务基础异常类
 *   <p>应用中的异常分为：未处理的异常和业务异常，controller层全局异常处理器捕获异常时会根据异常类型进行返回不同错误类型的消息。
 *   如果是业务层自定义的异常必须继承该类。
 *   </p>
 * @author kong
 * @date 2021/08/07 19:35
 * blog: http://blog.kongyin.ltd
 */
public abstract class AbstractBizException extends RuntimeException {
    public AbstractBizException() {
    }

    public AbstractBizException(String message) {
        super(message);
    }

    public AbstractBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractBizException(Throwable cause) {
        super(cause);
    }

    public AbstractBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
