/*
package com.ocp.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocp.common.bean.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

*/
/**
 * 默认统一的响应处理
 *
 * @author kong
 * @date 2021/08/13 21:50
 * blog: http://blog.kongyin.ltd
 *//*

//@RestControllerAdvice
@Slf4j
public class DefaultResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return false;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o instanceof String){
            return objectMapper.writeValueAsString(Result.succeed(o));
        }
        if(o instanceof Result){
            return o;
        }
        return Result.succeed(o);
    }
}
*/
