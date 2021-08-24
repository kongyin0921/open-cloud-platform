package com.ocp.gateway.handler;

import com.ocp.common.util.WebfluxResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 403拒绝访问异常处理，转换为JSON
 * @author kong
 * @date 2021/08/24 21:41
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
public class JsonAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        return WebfluxResponseUtil.responseFailed(serverWebExchange, HttpStatus.FORBIDDEN.value(), e.getMessage());
    }
}
