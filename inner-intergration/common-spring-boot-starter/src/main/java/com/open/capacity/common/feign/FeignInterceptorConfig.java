package com.open.capacity.common.feign;

import com.open.capacity.common.constant.TraceConstant;
import com.open.capacity.common.constant.UaaConstant;
import com.open.capacity.common.util.StringUtil;
import com.open.capacity.common.util.TokenUtil;
import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FeignInterceptorConfig
 * @Description TODO
 * @Auther kongyin
 * @Date 2020/10/28 15:01
 **/
@Configuration
public class FeignInterceptorConfig {

    public RequestInterceptor requestInterceptor(){
        RequestInterceptor requestInterceptor = requestTemplate -> {
            //token 传递
            //使用feign client访问别的微服务时，将accessToken header
            //config.anyRequest().permitAll() 非强制校验token
            if(StringUtils.isNotBlank(TokenUtil.getToken())){
                requestTemplate.header(UaaConstant.TOKEN_HEADER, TokenUtil.getToken());
            //template.header(UaaConstant.AUTHORIZATION,  OAuth2AccessToken.BEARER_TYPE  +  " "  +  TokenUtil.getToken() );
            }

            //传递traceId
            String traceId = StringUtil.isNotBlank(MDC.get(TraceConstant.LOG_TRACE_ID)) ? MDC.get(TraceConstant.LOG_TRACE_ID) : MDC.get(TraceConstant.LOG_B3_TRACEID);
            if (StringUtil.isNotBlank(traceId)) {
                requestTemplate.header(TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
            }

        };
        return requestInterceptor;
    }
}
