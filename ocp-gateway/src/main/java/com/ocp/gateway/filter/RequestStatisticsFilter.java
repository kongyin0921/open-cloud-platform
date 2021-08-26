package com.ocp.gateway.filter;


import cn.hutool.core.util.StrUtil;
import com.ocp.common.log.monitor.PointUtil;
import com.ocp.gateway.utils.ReactiveAddrUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 请求统计分析埋点过滤器
 * @author kong
 * @date 2021/08/25 22:27
 * blog: http://blog.kongyin.ltd
 */
public class RequestStatisticsFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Map<String, String> headers = request.getHeaders().toSingleValueMap();
        UserAgent userAgent = UserAgent.parseUserAgentString(headers.get("User-Agent"));
        //埋点
        PointUtil.debug("*", "request-statistics",
                StrUtil.format("ip={}&browser={}&operatingSystem={}",
                        ReactiveAddrUtil.getRemoteAddr(request),
                        getBrowser(userAgent.getBrowser().name()),
                        getOperatingSystem(userAgent.getOperatingSystem().name())
                ));
        return chain.filter(exchange);
    }

    private String getBrowser(String browser) {
        if (StrUtil.isNotEmpty(browser)) {
            if (browser.contains("CHROME")) {
                return "CHROME";
            } else if (browser.contains("FIREFOX")) {
                return "FIREFOX";
            } else if (browser.contains("SAFARI")) {
                return "SAFARI";
            } else if (browser.contains("EDGE")) {
                return "EDGE";
            }
        }
        return browser;
    }

    private String getOperatingSystem(String operatingSystem) {
        if (StrUtil.isNotEmpty(operatingSystem)) {
            if (operatingSystem.contains("MAC_OS_X")) {
                return "MAC_OS_X";
            } else if (operatingSystem.contains("ANDROID")) {
                return "ANDROID";
            }
        }
        return operatingSystem;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
