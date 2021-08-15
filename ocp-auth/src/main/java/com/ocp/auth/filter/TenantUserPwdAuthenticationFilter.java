package com.ocp.auth.filter;

import cn.hutool.core.util.StrUtil;
import com.ocp.auth.provider.token.TenantUserPwdAuthenticationToken;
import com.ocp.common.context.TenantContextHolder;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 替换 UsernamePasswordAuthenticationFilter 增加租户id
 * @author kong
 * @date 2021/08/15 16:20
 * blog: http://blog.kongyin.ltd
 */
@Setter
public class TenantUserPwdAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private boolean postOnly = true;

    public TenantUserPwdAuthenticationFilter () {
        super();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);
        String clientId = TenantContextHolder.getTenant();

        if (StrUtil.isEmpty(username)) {
            username = "";
        }

        if (StrUtil.isEmpty(password)) {
            password = "";
        }

        username = username.trim();

        TenantUserPwdAuthenticationToken authRequest = new TenantUserPwdAuthenticationToken(username, password, clientId);

        this.setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
