package com.ocp.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.ocp.common.constant.ConfigConstants;
import com.ocp.common.constant.MessageHeaderConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 传递负载均衡隔离值
 * @author kong
 * @date 2021/08/25 22:15
 * blog: http://blog.kongyin.ltd
 */
@Component
@SuppressWarnings("all")
@ConditionalOnProperty(name = ConfigConstants.CONFIG_RIBBON_ISOLATION_ENABLED, havingValue = "true")
public class LbIsolationFilter extends LoadBalancerClientFilter {
    public LbIsolationFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        String version = exchange.getRequest().getHeaders().getFirst(MessageHeaderConstants.O_C_P_VERSION);
        if (StrUtil.isNotEmpty(version)) {
            if (this.loadBalancer instanceof RibbonLoadBalancerClient) {
                RibbonLoadBalancerClient client = (RibbonLoadBalancerClient) this.loadBalancer;
                String serviceId = ((URI) Objects.requireNonNull(exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR))).getHost();
                return client.choose(serviceId, version);
            }
        }
        return super.choose(exchange);
    }
}
