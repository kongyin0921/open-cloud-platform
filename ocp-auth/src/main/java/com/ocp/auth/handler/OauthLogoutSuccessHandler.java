package com.ocp.auth.handler;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocp.common.bean.Result;
import com.ocp.common.code.CodeMsgs;
import com.ocp.common.constant.ParameterConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 注销成功处理程序
 * @author kong
 * @date 2021/08/14 11:31
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
public class OauthLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Resource
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String redirectUri = request.getParameter(ParameterConstants.REDIRECT_URI);
        if (StrUtil.isNotEmpty(redirectUri)) {
            //重定向自定地址
            redirectStrategy.sendRedirect(request,response,redirectUri);
        } else {
            response.setStatus(HttpStatus.OK.value());
            Result<Object> responseResult = Result.builder().codeMsg(CodeMsgs.REQUEST_SUCCESS).detail("登出成功").build();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().write(objectMapper.writeValueAsString(responseResult));
        }
    }
}
