package com.ocp.auth.provider;

import com.ocp.auth.provider.token.OpenIdAuthenticationToken;
import com.ocp.auth.provider.token.WxMiniProgramAuthenticationToken;
import com.ocp.auth.service.WxMiniProgramApiService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 微信小程序认证处理器
 * @author kong
 * @date 2021/08/19 20:23
 * blog: http://blog.kongyin.ltd
 */
@Configuration
public class WxMiniProgramProvider extends OpenIdAuthenticationProvider implements  InitializingBean {

    /**
     * 微信小程序操作API
     */
    @Autowired
    private WxMiniProgramApiService wxMiniProgramApiService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(wxMiniProgramApiService, "wxMiniProgramApiService must not be null ! ");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxMiniProgramAuthenticationToken token = (WxMiniProgramAuthenticationToken) authentication;
        String loginCode = token.getPrincipal().toString();
        Map<String,String> serviceResult = wxMiniProgramApiService.getOpenIdAndSessionKey(loginCode);
        String openId = serviceResult.get("openId");
        Authentication authenticate = super.authenticate(new OpenIdAuthenticationToken(openId, authentication.getAuthorities()));
        WxMiniProgramAuthenticationToken result = new WxMiniProgramAuthenticationToken(authenticate.getPrincipal(),authenticate.getAuthorities());
        result.setDetails(token.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return WxMiniProgramAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
