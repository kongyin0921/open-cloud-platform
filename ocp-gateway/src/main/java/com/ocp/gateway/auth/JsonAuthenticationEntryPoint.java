package com.ocp.gateway.auth;

import com.ocp.common.util.WebfluxResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 401未授权异常处理，转换为JSON
 * @author kong
 * @date 2021/08/24 21:51
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
public class JsonAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        return WebfluxResponseUtil.responseFailed(serverWebExchange, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }
}
