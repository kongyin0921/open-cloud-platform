package com.ocp.auth.provider.granter;

import com.ocp.auth.provider.token.WxMiniProgramAuthenticationToken;
import com.ocp.common.constant.GrantTypeConstant;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author kong
 * @date 2021/08/19 19:27
 * blog: http://blog.kongyin.ltd
 */
public class WxMiniProgramTokenGranter extends AbstractTokenGranter {
    /**
     * 授权类型
     */
    private static final String GRANT_TYPE = GrantTypeConstant.WX_MINI_PROGRAM;
    /**
     * 认证授权管理器
     */
    private final AuthenticationManager authenticationManager;

    public WxMiniProgramTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetails, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetails, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String wxLoginCode = parameters.get("code");
        if (StringUtils.isEmpty(wxLoginCode)) {
            throw new InvalidRequestException("微信小程序登录code不能为空!");
        }
        AbstractAuthenticationToken authentication = new WxMiniProgramAuthenticationToken(wxLoginCode, wxLoginCode);
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
            throw new InvalidGrantException("Could not authenticate wechat user code: " + wxLoginCode);
        }
    }
}
