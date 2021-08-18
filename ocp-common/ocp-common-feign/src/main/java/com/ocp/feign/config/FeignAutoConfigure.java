package com.ocp.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author kong
 * @date 2021/08/17 21:06
 * blog: http://blog.kongyin.ltd
 */
public class FeignAutoConfigure {
    /**
     * Feign 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
