package com.ycz.platform.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置属性
 * @author kong
 * @date 2020-10-14 21:32
 */
@Data
@ConfigurationProperties(prefix = "spring.redis.redisson")
public class RedissonProperties {
    private String config;
    private String enable;
}
