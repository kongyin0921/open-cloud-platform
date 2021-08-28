package com.ocp.common.search.annotation;

import com.ocp.common.search.client.feign.fallback.SearchServiceFallbackFactory;
import com.ocp.common.search.client.service.Impl.QueryServiceImpl;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制是否加载搜索中心客户端的Service
 * @author kong
 * @date 2021/08/28 14:31
 * blog: http://blog.kongyin.ltd
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableFeignClients(basePackages = {"com.ocp"})
@Import({QueryServiceImpl.class, SearchServiceFallbackFactory.class})
public @interface EnableSearchClient {
}
