package com.ocp.common.config;

import com.ocp.common.feign.UserService;
import com.ocp.common.resolver.ClientArgumentResolver;
import com.ocp.common.resolver.TokenArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 默认SpringMVC拦截器
 * @author kong
 * @date 2021/08/07 18:13
 * blog: http://blog.kongyin.ltd
 */
public abstract class AbstractWebMvcConfig implements WebMvcConfigurer {

    @Lazy
    @Autowired
    private UserService userService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //注入用户信息
        resolvers.add(new TokenArgumentResolver(userService));
        //注入应用信息
        resolvers.add(new ClientArgumentResolver());
    }
}
