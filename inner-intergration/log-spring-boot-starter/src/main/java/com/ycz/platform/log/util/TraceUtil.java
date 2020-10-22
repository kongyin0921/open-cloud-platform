package com.ycz.platform.log.util;

import com.open.capacity.common.constant.TraceConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName TraceUtil
 * @Description api 经过filter--> interceptor -->aop -->controller 如果某些接口，比如filter
 *          --> userdetail 这种情况，aop mdc设置 后续log输出traceid
 * @Auther kongyin
 * @Date 2020/10/22 14:40
 **/
public class TraceUtil {

    public static String getTrace() {
        String appTraceId = "";
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();

            appTraceId = request.getHeader(TraceConstant.HTTP_HEADER_TRACE_ID);

            // 未经过HandlerInterceptor的设置
            if (StringUtils.isBlank(MDC.get(TraceConstant.LOG_TRACE_ID))) {
                // 但是有请求头，重新设置
                if (StringUtils.isNotEmpty(appTraceId)) {
                    MDC.put(TraceConstant.LOG_TRACE_ID, appTraceId);
                }
            }
        } catch (Exception e) {

        }

        return appTraceId;

    }

}
