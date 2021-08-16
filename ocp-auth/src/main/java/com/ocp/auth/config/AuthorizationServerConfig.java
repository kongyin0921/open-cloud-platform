package com.ocp.auth.config;

import com.ocp.auth.provider.client.RedisClientDetailsService;
import com.ocp.auth.provider.code.RedisAuthorizationCodeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *  OAuth2 授权服务器配置
 * @author kong
 * @date 2021/08/15 20:32
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@EnableAuthorizationServer
@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 自定义 redis 客户端详情服务 替代 JdbcClientDetailsService
     */
    @Autowired
    private RedisClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore tokenStore;

    /**
     * 注入authenticationManager 来支持 password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 自定义 redis AuthorizationCodeServices 授权码服务
     */
    @Autowired
    private RedisAuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private WebResponseExceptionTranslator exceptionTranslator;

    /**
     * 授权服务安全配置
     * 对应于配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许客户表单认证 让/oauth/token支持client_id以及client_secret作登录认证
        security.allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder)//设置oauth_client_details中的密码编码器
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置应用名称 应用id
     * 配置OAuth2的客户端相关信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //JDBC 保存客户端信息, 对应的表：oauth_client_details 使用redis做缓存处理
        clients.withClientDetails(clientDetailsService);
        //将oauth_client_details全表刷入redis
        clientDetailsService.loadAllClientToCache();
    }

    /**
     * 配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager)
                .authorizationCodeServices(authorizationCodeServices)
                .exceptionTranslator(exceptionTranslator);

    }
}
