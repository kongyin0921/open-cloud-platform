package com.ocp.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 开启 redis session 共享
 * @author kong
 * @date 2021/08/03 21:46
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
