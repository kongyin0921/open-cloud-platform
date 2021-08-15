package com.ocp.auth.config;

import com.ocp.auth.handler.OauthLogoutHandler;
import com.ocp.auth.provider.error.RestWebResponseExceptionTranslator;
import com.ocp.common.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证错误处理
 * @author kong
 * @date 2021/08/14 17:35
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@Slf4j
public class SecurityHandlerConfig {
    @Bean
    public LogoutHandler logoutHandler() {
        return new OauthLogoutHandler();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws ServletException, IOException {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    @Bean
    public WebResponseExceptionTranslator<Result<Object>> webResponseExceptionTranslator() {
        return new RestWebResponseExceptionTranslator();
    }
}
