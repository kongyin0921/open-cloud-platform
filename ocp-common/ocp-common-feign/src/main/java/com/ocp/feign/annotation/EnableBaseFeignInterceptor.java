package com.ocp.feign.annotation;

import com.ocp.feign.config.FeignInterceptorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * 开启feign拦截器传递数据给下游服务，只包含基础数据
 * @author kong
 * @date 2021/08/04 20:44
 * blog: http://blog.kongyin.ltd
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(FeignInterceptorConfig.class)
public @interface EnableBaseFeignInterceptor {
}
