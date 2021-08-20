package com.ocp.auth.provider.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collection;

/**
 * @author kong
 * @date 2021/08/19 20:22
 * blog: http://blog.kongyin.ltd
 */
public class WxMiniProgramAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -4120151193723851357L;

    private final Object principal;
    private Object credentials;

    public WxMiniProgramAuthenticationToken(Object principal, Object credentials) {
        super((Collection)null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
