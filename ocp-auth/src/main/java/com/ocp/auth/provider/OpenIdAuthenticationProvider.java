package com.ocp.auth.provider;

import com.ocp.auth.provider.token.OpenIdAuthenticationToken;
import com.ocp.auth.service.factory.UserDetailServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author kong
 * @date 2021/08/15 15:10
 * blog: http://blog.kongyin.ltd
 */
@Configuration
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailServiceFactory userDetailsServiceFactory;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;
        String openId = authenticationToken.getPrincipal().toString();
        UserDetails user = userDetailsServiceFactory.getService(authenticationToken).loadUserByOpenId(openId);
        if (user == null) {
            throw new InternalAuthenticationServiceException("openId错误");
        }
        OpenIdAuthenticationToken result = new OpenIdAuthenticationToken(user, user.getAuthorities());
        result.setDetails(authenticationToken.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
