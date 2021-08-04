package com.ocp.feign.annotation;

import com.ocp.feign.config.FeignHttpInterceptorConfig;
import com.ocp.feign.config.FeignInterceptorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启feign拦截器传递数据给下游服务，包含基础数据和http的相关数据
 * @author kong
 * @date 2021/08/04 21:06
 * blog: http://blog.kongyin.ltd
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import({FeignInterceptorConfig.class, FeignHttpInterceptorConfig.class})
public @interface EnableFeignInterceptor {
}
