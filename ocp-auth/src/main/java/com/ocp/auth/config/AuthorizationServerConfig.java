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
 *  OAuth2 ?????????????????????
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
     * ????????????????????????????????????
     */
    @Value("${ocp.auth.isSingleLogin:false}")
    private boolean isSingleLogin;


    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ????????? redis ????????????????????? ?????? JdbcClientDetailsService
     */
    @Autowired
    private RedisClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore tokenStore;

    /**
     * ??????authenticationManager ????????? password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * ????????? redis AuthorizationCodeServices ???????????????
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
     * ????????????????????????
     * ???????????????AuthorizationServer????????????????????????????????????ClientCredentialsTokenEndpointFilter???????????????
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //???????????????????????? ???/oauth/token??????client_id??????client_secret???????????????
        security.allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder)//??????oauth_client_details?????????????????????
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * ?????????????????? ??????id
     * ??????OAuth2????????????????????????
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //JDBC ?????????????????????, ???????????????oauth_client_details ??????redis???????????????
        clients.withClientDetails(clientDetailsService);
        //???oauth_client_details????????????redis
        clientDetailsService.loadAllClientToCache();
    }

    /**
     * ?????????????????????????????????????????????TokenStore???TokenGranter???OAuth2RequestFactory
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
        //????????????
        endpoints.tokenGranter(new CompositeTokenGranter(getAllTokenGranters(clientDetails, tokenServices, requestFactory)));
    }

    /**
     * tokenGranters??????oauth?????? ????????????/oauth/token??????????????????????????????AbstractTokenGranter ??????
     * ??????????????????????????????5????????? + ??????????????????
     * @param clientDetails
     * @param tokenServices
     * @param requestFactory
     */
    private List<TokenGranter> getAllTokenGranters(ClientDetailsService clientDetails,
                                                   AuthorizationServerTokenServices tokenServices,
                                                   OAuth2RequestFactory requestFactory) {

        //???????????????????????????
        List<TokenGranter> tokenGranters = getDefaultTokenGranters(tokenServices, authorizationCodeServices, requestFactory);
        if (authenticationManager != null) {
            // ????????????????????????????????????
            tokenGranters.add(new PwdValidateCodeGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory, validateCodeService));
            // ??????openId??????
            tokenGranters.add(new OpenIdGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
            // ????????????????????????????????????
            tokenGranters.add(new MobilePwdGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
            // wx ?????????
            tokenGranters.add(new WxMiniProgramTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
        }
        return tokenGranters;
    }

    /**
     * ?????????????????????
     */
    private List<TokenGranter> getDefaultTokenGranters(AuthorizationServerTokenServices tokenServices
            , AuthorizationCodeServices authorizationCodeServices, OAuth2RequestFactory requestFactory) {
        List<TokenGranter> tokenGranters = new ArrayList<>();
        // ?????????????????????
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory));
        // ???????????????????????????
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // ????????????????????????
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // ?????????????????????
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
        if (authenticationManager != null) {
            // ??????????????????
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
        }
        return tokenGranters;
    }

    /**
     * token ????????????
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
        // ?????????token??????
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
