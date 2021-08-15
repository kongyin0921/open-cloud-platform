package com.ocp.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 * @author kong
 * @date 2021/08/11 20:53
 * blog: http://blog.kongyin.ltd
 */
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = -7285211528095468156L;
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}