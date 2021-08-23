package com.ocp.common.config;

import com.ocp.common.filter.UserDetailsExtractFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author kong
 * @date 2021/08/22 15:40
 * blog: http://blog.kongyin.ltd
 */
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean userDetailsExtractFilterRegister() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //注入过滤器
        registration.setFilter(new UserDetailsExtractFilter());
        //拦截规则
        registration.addUrlPatterns("/*");
        //过滤器名称
        registration.setName("userDetailsExtractFilter");
        //过滤器顺序
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

}
