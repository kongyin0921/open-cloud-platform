package com.ocp.auth.handler;

import com.ocp.common.constant.EndpointConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份验证登录失败处理程序
 * 为将来 发送事件 存储日志做准备
 * @author kong
 * @date 2021/08/16 22:28
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Component
public class AuthLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public AuthLoginFailureHandler() {
        super(EndpointConstant.LOGIN_FAILURE_PAGE);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
    }
}
