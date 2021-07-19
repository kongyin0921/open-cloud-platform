package com.ocp.common.redis.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

/**
 * redis 配置类
 * @author kong
 * @date 2021/07/19 20:05
 * blog: http://blog.kongyin.ltd
 */
@Data
@EnableCaching
@ConfigurationProperties(prefix = "ocp.cache-manager")
public class CacheManagerProperties {
    private List<CacheConfig> configs;

    @Setter
    @Getter
    public static class CacheConfig {
        /**
         * cache key
         */
        private String key;
        /**
         * 过期时间，sec
         */
        private long second = 60;
    }
}
