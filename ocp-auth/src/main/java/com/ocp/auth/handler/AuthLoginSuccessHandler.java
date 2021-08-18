package com.ocp.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份验证登录成功处理程序
 * 为将来 发送事件 存储日志做准备
 * @author kong
 * @date 2021/08/16 22:25
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Component
public class AuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        log.info("用户：{} 登录成功", authentication.getPrincipal());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
