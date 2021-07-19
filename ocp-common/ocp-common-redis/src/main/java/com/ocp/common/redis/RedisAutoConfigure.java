package com.ocp.common.redis;

import com.ocp.common.redis.properties.CacheManagerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * redis 配置类
 * @author kong
 * @date 2021/07/19 19:59
 * blog: http://blog.kongyin.ltd
 */
@EnableConfigurationProperties({RedisProperties.class, CacheManagerProperties.class})
public class RedisAutoConfigure {

    @Autowired
    private CacheManagerProperties properties;

    @Bean
    public RedisSerializer<String> redisKeySerializer(){
        return RedisSerializer.string();
    }

    @Bean
    public RedisSerializer<Object> redisValueSerializer(){
        return RedisSerializer.json();
    }


    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory,
                                                      RedisSerializer<String> redisKeySerializer,
                                                      RedisSerializer<Object> redisValueSerializer){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(redisKeySerializer);
        redisTemplate.setHashKeySerializer(redisKeySerializer);
        redisTemplate.setDefaultSerializer(redisValueSerializer);
        return redisTemplate;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory factory,
                                     RedisSerializer<String> redisKeySerializer,
                                     RedisSerializer<Object> redisValueSerializer){
        RedisCacheConfiguration difConf = getDefConf(redisKeySerializer,redisValueSerializer).entryTtl(Duration.ofHours(1));

        //自定义的缓存过期时间配置
        int configSize = properties.getConfigs() == null ? 0 : properties.getConfigs().size();
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        if (configSize > 0) {
            properties.getConfigs().forEach(cacheConfig -> {
                RedisCacheConfiguration conf = getDefConf(redisKeySerializer, redisValueSerializer).entryTtl(Duration.ofSeconds(cacheConfig.getSecond()));
                redisCacheConfigurationMap.put(cacheConfig.getKey(),conf);
            });
        }
        return RedisCacheManager.builder(factory).cacheDefaults(difConf)
                .withInitialCacheConfigurations(redisCacheConfigurationMap).build();

    }

    private RedisCacheConfiguration getDefConf(RedisSerializer<String> redisKeySerializer, RedisSerializer<Object> redisValueSerializer) {
        return RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                .computePrefixWith(cacheName -> "cache".concat(":").concat(cacheName).concat(":"))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisKeySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisValueSerializer));
    }

    @Bean
    public KeyGenerator keyGenerator(){
        return (target, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":" + method.getName() + ":");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }


}
