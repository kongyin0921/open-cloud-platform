package com.ocp.auth.provider;


import com.ocp.auth.provider.token.TenantUserPwdAuthenticationToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 增加租户id，解决不同租户单点登录时角色没变化
 * @author kong
 * @date 2021/08/15 15:17
 * blog: http://blog.kongyin.ltd
 */
@Configuration
public class TenantAuthenticationProvider extends PasswordAuthenticationProvider {

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {

        TenantUserPwdAuthenticationToken authenticationToken = (TenantUserPwdAuthenticationToken) authentication;
        TenantUserPwdAuthenticationToken result = new TenantUserPwdAuthenticationToken(principal, authentication.getCredentials(),
                user.getAuthorities(), authenticationToken.getClientId());
        result.setDetails(authenticationToken.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TenantUserPwdAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
