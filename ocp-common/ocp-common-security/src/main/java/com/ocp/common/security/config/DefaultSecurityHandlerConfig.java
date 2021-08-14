package com.ocp.common.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocp.common.bean.Result;
import com.ocp.common.code.CodeMsg;
import com.ocp.common.code.CodeMsgs;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 默认安全处理程序配置
 * @author kong
 * @date 2021/07/31 9:58
 * blog: http://blog.kongyin.ltd
 */
public class DefaultSecurityHandlerConfig {

    @Resource
    private ObjectMapper objectMapper;
    /**
     * 未登录，返回401
     *
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) -> responseRestResultHandle(request, response, authException);
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext){
        OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler = new OAuth2WebSecurityExpressionHandler();
        oAuth2WebSecurityExpressionHandler.setApplicationContext(applicationContext);
        return oAuth2WebSecurityExpressionHandler;
    }

    /**
     * 处理spring security oauth 处理失败返回消息格式
     */
    @Bean
    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler(){
        return new OAuth2AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
                responseRestResultHandle(request, response, authException);;
            }
        };
    }


    /**
     * 异常响应结果处理
     *
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     */
    private void responseRestResultHandle(HttpServletRequest request, HttpServletResponse response,
                                          Exception exception) throws IOException {
        CodeMsg codeMsg = CodeMsgs.SYSTEM_BASE_ERROR;
        if (exception instanceof AccessDeniedException) {
            codeMsg = CodeMsgs.ACCESS_FAIL;
        } else if (exception instanceof AuthenticationException) {
            codeMsg = CodeMsgs.AUTH_FAIL;
        }
        String message = exception.getLocalizedMessage();
        Result<Object> responseResult = Result.builder().codeMsg(codeMsg).detail(message).build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(objectMapper.writeValueAsString(responseResult));
    }
}
