package com.ocp.auth.handler;

import cn.hutool.core.util.StrUtil;
import com.ocp.common.security.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销处理程序
 * @author kong
 * @date 2021/08/14 17:09
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Component
public class AuthLogoutHandler implements LogoutHandler {

    private final TokenStore tokenStore;

    public AuthLogoutHandler(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Assert.isNull(tokenStore,"tokenStore must be set");
        String token = request.getParameter("token");
        if (StrUtil.isEmpty(token)) {
             token = AuthUtils.extractToken(request);
        }
        if (StrUtil.isNotEmpty(token)) {
            OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
            if (existingAccessToken != null) {
                if (existingAccessToken.getRefreshToken() != null) {
                    log.info("remove refreshToken :{}!", existingAccessToken.getRefreshToken());
                    tokenStore.removeRefreshToken(existingAccessToken.getRefreshToken());
                }
                log.info("remove existingAccessToken :{}!", existingAccessToken);
                tokenStore.removeAccessToken(existingAccessToken);
            }
        }
    }
}
