package com.ocp.auth.provider.granter;


import com.ocp.auth.provider.token.MobileAuthenticationToken;
import com.ocp.common.constant.GrantTypeConstant;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 电话密码授权模式
 * @author kong
 * @date 2021/08/15 10:57
 * blog: http://blog.kongyin.ltd
 */
public class MobilePwdGranter extends AbstractTokenGranter {
    /**
     * 授权类型
     */
    private static final String GRANT_TYPE = GrantTypeConstant.MOBILE_PASSWORD;
    /**
     * 认证授权管理
     */
    private final AuthenticationManager authenticationManager;

    public MobilePwdGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                            OAuth2RequestFactory requestFactory,AuthenticationManager authenticationManager) {
        super(tokenServices, clientDetailsService, requestFactory,GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String mobile = parameters.get("mobile");
        String password = parameters.get("password");
        // 防止下游密码泄露
        parameters.remove("password");

        AbstractAuthenticationToken authentication = new MobileAuthenticationToken(mobile, password);
        authentication.setDetails(parameters);

        Authentication userAuth;
        try {
            userAuth = this.authenticationManager.authenticate(authentication);
        } catch (AccountStatusException | BadCredentialsException var8) {
            throw new InvalidGrantException(var8.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            throw new InvalidGrantException("Could not authenticate mobile: " + mobile);
        }
    }
}
