package com.ocp.gateway.config;

import com.ocp.common.security.porperties.SecurityProperties;
import com.ocp.gateway.auth.CustomAuthenticationManager;
import com.ocp.gateway.auth.JsonAuthenticationEntryPoint;
import com.ocp.gateway.auth.PermissionAuthManager;
import com.ocp.gateway.handler.JsonAccessDeniedHandler;
import com.ocp.gateway.handler.Oauth2AuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;

/**
 * 资源服务器配置
 * @author kong
 * @date 2021/08/25 21:16
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@SuppressWarnings("all")
public class ResourceServerConfiguration {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PermissionAuthManager permissionAuthManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        //认证处理器
        CustomAuthenticationManager authenticationManager = new CustomAuthenticationManager(tokenStore);
        JsonAuthenticationEntryPoint authenticationEntryPoint = new JsonAuthenticationEntryPoint();
        //token转换器
        ServerBearerTokenAuthenticationConverter authenticationConverter = new ServerBearerTokenAuthenticationConverter();
        authenticationConverter.setAllowUriQueryParameter(true);
        //oauth2认证过滤器
        AuthenticationWebFilter webFilter = new AuthenticationWebFilter(authenticationManager);
        webFilter.setServerAuthenticationConverter(authenticationConverter);
        webFilter.setAuthenticationSuccessHandler(new Oauth2AuthSuccessHandler());
        webFilter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(authenticationEntryPoint));
        httpSecurity.addFilterAt(webFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec = httpSecurity.authorizeExchange();
        //添加拦截路径
        if (securityProperties.getAuth().getHttpUrls().length > 0) {
            authorizeExchangeSpec.pathMatchers(securityProperties.getAuth().getHttpUrls()).authenticated();
        }
        //添加忽略路径
        if (securityProperties.getIgnore().getHttpUrls().length > 0) {
            authorizeExchangeSpec.pathMatchers(securityProperties.getIgnore().getHttpUrls()).permitAll();
        }

        authorizeExchangeSpec.pathMatchers(HttpMethod.OPTIONS).permitAll().anyExchange().access(permissionAuthManager)
                .and().exceptionHandling().accessDeniedHandler(new JsonAccessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint)
                .and().headers().frameOptions().disable().and().httpBasic().disable().csrf().disable();

        return httpSecurity.build();
    }
}
