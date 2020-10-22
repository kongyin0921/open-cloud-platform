package com.ycz.platform.log.config;

import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.github.structlog4j.StructLog4J;
import com.github.structlog4j.json.JsonFormatter;
import com.open.capacity.common.util.IPUtils;
import com.ycz.platform.log.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * @ClassName LogAutoConfig
 * @Description 日志拦截器，排除对spring cloud gateway的影响 (WebMvcConfigurer)
 * @Auther kongyin
 * @Date 2020/10/22 9:57
 **/
public class LogAutoConfig implements WebMvcConfigurer {

    @Value("${spring.application.name:NA}")
    private String appName;

    @PostConstruct
    public void init(){
        StructLog4J.setFormatter(JsonFormatter.getInstance());
        StructLog4J.setMandatoryContextSupplier(()-> new Object[]{
            "host",	IPUtils.getHostIp(),
            "appName",appName ,
            "logTime", SystemClock.nowDate()
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
    }
}
