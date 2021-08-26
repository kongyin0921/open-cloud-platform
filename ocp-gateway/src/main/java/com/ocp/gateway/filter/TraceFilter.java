package com.ocp.gateway.filter;

import cn.hutool.core.util.IdUtil;
import com.ocp.common.constant.MessageHeaderConstants;
import com.ocp.common.constant.ParameterConstants;
import com.ocp.common.log.properties.TraceProperties;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 生成日志链路追踪id，并传入header中
 * @author kong
 * @date 2021/08/25 22:27
 * blog: http://blog.kongyin.ltd
 */
@Component
public class TraceFilter implements GlobalFilter, Ordered {

    @Autowired
    private TraceProperties traceProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (traceProperties.getEnable()) {
            //链路追踪id
            String traceId = IdUtil.fastSimpleUUID();
            MDC.put(ParameterConstants.LOG_TRACE_ID, traceId);
            ServerHttpRequest request = exchange.getRequest().mutate().headers(
                    httpHeaders -> httpHeaders.add(MessageHeaderConstants.TRACE_ID_HEADER, traceId)
            ).build();

            ServerWebExchange webExchange = exchange.mutate().request(request).build();
            chain.filter(webExchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
