package com.ocp.feign.ribbon;

import com.ocp.feign.properties.RestTemplateProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.DefaultPropertiesFactory;
import org.springframework.context.annotation.Bean;

/**
 * @author kong
 * @date 2021/08/04 22:35
 * blog: http://blog.kongyin.ltd
 */
@EnableConfigurationProperties(RestTemplateProperties.class)
public class RibbonAutoConfigure {
    @Bean
    public DefaultPropertiesFactory defaultPropertiesFactory() {
        return new DefaultPropertiesFactory();
    }
}
