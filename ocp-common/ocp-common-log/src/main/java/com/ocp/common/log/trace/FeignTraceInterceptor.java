package com.ocp.common.log.trace;

import cn.hutool.core.util.StrUtil;
import com.ocp.common.log.properties.TraceProperties;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * feign拦截器，传递traceId
 * @author kong
 * @date 2021/08/08 12:34
 * blog: http://blog.kongyin.ltd
 */
@ConditionalOnClass(value = {RequestInterceptor.class})
public class FeignTraceInterceptor {
    @Resource
    private TraceProperties traceProperties;

    @Bean
    public RequestInterceptor feignTraceInterceptor() {
        return template -> {
            if (traceProperties.getEnable()) {
                //传递日志traceId
                String traceId = MDCTraceUtils.getTraceId();
                if (StrUtil.isNotEmpty(traceId)) {
                    template.header(MDCTraceUtils.TRACE_ID_HEADER, traceId);
                }
            }
        };
    }
}
