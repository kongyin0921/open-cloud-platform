package com.ocp.gateway.config;

import com.ocp.gateway.handler.JsonErrorWebExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * 自定义异常处理
 * @author kong
 * @date 2021/08/25 21:00
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(WebFluxConfigurer.class)
@AutoConfigureBefore(WebFluxAutoConfiguration.class)
@EnableConfigurationProperties({ServerProperties.class, ResourceProperties.class})
@SuppressWarnings("all")
public class CustomErrorWebFluxAutoConfiguration {
    private final ServerProperties serverProperties;

    private final ResourceProperties resourceProperties;

    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;

    private final ApplicationContext applicationContext;

    public CustomErrorWebFluxAutoConfiguration(ServerProperties serverProperties, ResourceProperties resourceProperties,
                                               ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                               ServerCodecConfigurer serverCodecConfigurer,
                                               ApplicationContext applicationContext) {
        this.serverProperties = serverProperties;
        this.resourceProperties = resourceProperties;
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);;
        this.serverCodecConfigurer = serverCodecConfigurer;
        this.applicationContext = applicationContext;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
        JsonErrorWebExceptionHandler jsonErrorWebExceptionHandler = new JsonErrorWebExceptionHandler(
                errorAttributes, this.resourceProperties, this.serverProperties.getError(), this.applicationContext);

        jsonErrorWebExceptionHandler.setViewResolvers(this.viewResolvers);
        jsonErrorWebExceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
        jsonErrorWebExceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());

        return jsonErrorWebExceptionHandler;
    }
}
