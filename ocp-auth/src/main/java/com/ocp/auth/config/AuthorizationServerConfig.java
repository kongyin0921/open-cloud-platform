package com.ocp.auth.config;

import com.ocp.auth.provider.client.RedisClientDetailsService;
import com.ocp.auth.provider.code.RedisAuthorizationCodeServices;
import com.ocp.auth.provider.granter.MobilePwdGranter;
import com.ocp.auth.provider.granter.OpenIdGranter;
import com.ocp.auth.provider.granter.PwdValidateCodeGranter;
import com.ocp.auth.provider.granter.WxMiniProgramTokenGranter;
import com.ocp.auth.provider.token.CustomTokenEnhancer;
import com.ocp.auth.provider.token.CustomTokenServices;
import com.ocp.auth.service.IValidateCodeService;
import com.ocp.auth.service.factory.UserDetailServiceFactory;
import com.ocp.auth.service.factory.UserDetailsByNameServiceFactoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  OAuth2 授权服务器配置
 * @author kong
 * @date 2021/08/15 20:32
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@EnableAuthorizationServer
@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
@SuppressWarnings("all")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final boolean reuseRefreshToken = true;
    /**
     * 是否登录同应用同账号互踢
     */
    @Value("${ocp.auth.isSingleLogin:false}")
    private boolean isSingleLogin;


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

    @Resource
    private UserDetailServiceFactory userDetailsServiceFactory;

    @Autowired
    private CustomTokenEnhancer tokenEnhancer;

    @Autowired
    private IValidateCodeService validateCodeService;


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
                .exceptionTranslator(exceptionTranslator).tokenServices(tokenServices());

        ClientDetailsService clientDetails = endpoints.getClientDetailsService();
        AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
        OAuth2RequestFactory requestFactory = endpoints.getOAuth2RequestFactory();
        //组合模式
        endpoints.tokenGranter(new CompositeTokenGranter(getAllTokenGranters(clientDetails, tokenServices, requestFactory)));
    }

    /**
     * tokenGranters添加oauth模式 ，可以让/oauth/token支持自定义模式，继承AbstractTokenGranter 扩展
     * 所有授权模式：默认的5种模式 + 自定义的模式
     * @param clientDetails
     * @param tokenServices
     * @param requestFactory
     */
    private List<TokenGranter> getAllTokenGranters(ClientDetailsService clientDetails,
                                                   AuthorizationServerTokenServices tokenServices,
                                                   OAuth2RequestFactory requestFactory) {

        //获取默认的授权模式
        List<TokenGranter> tokenGranters = getDefaultTokenGranters(tokenServices, authorizationCodeServices, requestFactory);
        if (authenticationManager != null) {
            // 添加密码加图形验证码模式
            tokenGranters.add(new PwdValidateCodeGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory, validateCodeService));
            // 添加openId模式
            tokenGranters.add(new OpenIdGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
            // 添加手机号加密码授权模式
            tokenGranters.add(new MobilePwdGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
            // wx 小程序
            tokenGranters.add(new WxMiniProgramTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
        }
        return tokenGranters;
    }

    /**
     * 默认的授权模式
     */
    private List<TokenGranter> getDefaultTokenGranters(AuthorizationServerTokenServices tokenServices
            , AuthorizationCodeServices authorizationCodeServices, OAuth2RequestFactory requestFactory) {
        List<TokenGranter> tokenGranters = new ArrayList<>();
        // 添加授权码模式
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory));
        // 添加刷新令牌的模式
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 添加隐士授权模式
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 添加客户端模式
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
        if (authenticationManager != null) {
            // 添加密码模式
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
        }
        return tokenGranters;
    }

    /**
     * token 生成规则
     *
     * @return
     */
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new CustomTokenServices(isSingleLogin);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(reuseRefreshToken);
        tokenServices.setClientDetailsService(clientDetailsService);
        // 自定义token处理
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Collections.singletonList(tokenEnhancer));
        tokenServices.setTokenEnhancer(enhancerChain);
        addUserDetailsService(tokenServices);
        return tokenServices;
    }

    private void addUserDetailsService(DefaultTokenServices tokenServices) {
        if (this.userDetailsServiceFactory != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceFactoryWrapper<>(this.userDetailsServiceFactory));
            tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
        }
    }
}
