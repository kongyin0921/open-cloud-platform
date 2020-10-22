package com.ycz.platform.log.interceptor;

import com.open.capacity.common.constant.TraceConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LogInterceptor
 * @Description 首先创建拦截器，加入拦截列表中，在请求到达时生成traceId。
 * @Auther kongyin
 * @Date 2020/10/22 10:17
 **/
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(TraceConstant.HTTP_HEADER_TRACE_ID);
        if(StringUtils.isNotEmpty(traceId)){
            MDC.put(TraceConstant.LOG_TRACE_ID, traceId);
        }
        return true;
    }
}
