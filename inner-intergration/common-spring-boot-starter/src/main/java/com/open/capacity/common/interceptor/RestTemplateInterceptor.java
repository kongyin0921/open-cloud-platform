package com.open.capacity.common.interceptor;

import com.open.capacity.common.constant.TraceConstant;
import com.open.capacity.common.constant.UaaConstant;
import com.open.capacity.common.util.StringUtil;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName RestTemplateInterceptor
 * @Description TODO
 * @Auther kongyin
 * @Date 2020/10/28 15:37
 **/
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader(UaaConstant.AUTHORIZATION);

        String token = StringUtil.isBlank(StringUtil.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ")) ? request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) : StringUtil.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ");
        //传递token
        HttpHeaders headers = httpRequest.getHeaders();
        headers.add(UaaConstant.TOKEN_HEADER,token);
        //传递traceId
        String traceId = StringUtil.isNotEmpty(MDC.get(TraceConstant.LOG_TRACE_ID)) ? MDC.get(TraceConstant.LOG_TRACE_ID) : MDC.get(TraceConstant.LOG_B3_TRACEID);
        if(StringUtil.isNotEmpty(traceId)){
            headers.add(TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
        }
        // 保证请求继续执行
        return clientHttpRequestExecution.execute(httpRequest,bytes);
    }
}
