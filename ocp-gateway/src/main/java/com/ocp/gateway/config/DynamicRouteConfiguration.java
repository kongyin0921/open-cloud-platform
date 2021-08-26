package com.ocp.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.ocp.gateway.route.NacosRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kong
 * @date 2021/08/26 21:53
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@ConditionalOnProperty(prefix = "ocp.gateway.dynamicRoute", name = "enabled", havingValue = "true")
public class DynamicRouteConfiguration {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Configuration
    @ConditionalOnProperty(prefix = "ocp.gateway.dynamicRoute", name = "dataType", havingValue = "nacos", matchIfMissing = true)
    public class NacosDynamicRoute {
        @Autowired
        private NacosConfigProperties nacosConfigProperties;

        @Bean
        public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
            return new NacosRouteDefinitionRepository(publisher, nacosConfigProperties);
        }
    }
}
