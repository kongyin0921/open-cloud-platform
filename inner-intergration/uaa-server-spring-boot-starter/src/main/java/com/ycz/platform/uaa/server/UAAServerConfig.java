package com.ycz.platform.uaa.server;

import com.open.capacity.common.feign.FeignInterceptorConfig;
import com.open.capacity.common.rest.RestTemplateConfig;
import com.ycz.platform.uaa.server.service.RedisClientDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

/**
 * @ClassName UAAServerConfig
 * @Description
 * @Auther kongyin
 * @Date 2020/10/28 14:28
 **/
@Configuration
@SuppressWarnings("all")
@Import({RestTemplateConfig.class, FeignInterceptorConfig.class})
public class UAAServerConfig {

    @Bean
    public RedisClientDetailsService redisClientDetailsService(DataSource dataSource, RedisTemplate<String, Object> redisTemplate){
        return null;
    }
}
