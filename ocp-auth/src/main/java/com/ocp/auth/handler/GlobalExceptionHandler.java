package com.ocp.auth.handler;

import com.ocp.common.handler.AbstractExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author kong
 * @date 2021/08/21 19:00
 * blog: http://blog.kongyin.ltd
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends AbstractExceptionHandler {
}
