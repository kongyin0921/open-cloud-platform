package com.ocp.feign.config;

import cn.hutool.core.util.StrUtil;
import com.ocp.common.constant.MessageHeaderConstants;
import com.ocp.common.context.TenantContextHolder;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * feign拦截器，只包含基础数据
 * @author kong
 * @date 2021/08/04 21:10
 * blog: http://blog.kongyin.ltd
 */
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return template -> {
            String tenant = TenantContextHolder.getTenant();
            if (StrUtil.isNotEmpty(tenant)) {
                template.header(MessageHeaderConstants.TENANT_HEADER, tenant);
            }
        };
    }
}
