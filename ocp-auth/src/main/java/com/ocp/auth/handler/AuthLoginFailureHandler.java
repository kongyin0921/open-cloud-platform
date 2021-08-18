package com.ocp.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocp.common.bean.Result;
import com.ocp.common.code.CodeMsgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 身份验证登录失败处理程序
 * 为将来 发送事件 存储日志做准备
 * @author kong
 * @date 2021/08/16 22:28
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Component
public class AuthLoginFailureHandler implements AuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String message;
        if (e instanceof BadCredentialsException) {
            message = "用户名或密码错误！";
        } else if (e instanceof LockedException) {
            message = "用户已被锁定！";
        } else {
            message = "认证失败，请联系网站管理员！";
        }
        Result<Object> responseResult = Result.builder().codeMsg(CodeMsgs.AUTH_FAIL).detail(message).build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(objectMapper.writeValueAsString(responseResult));
    }


}
