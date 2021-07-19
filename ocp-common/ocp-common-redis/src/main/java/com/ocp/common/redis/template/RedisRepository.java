package com.ocp.common.redis.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis Repository
 * redis 基本操作 可扩展,基本够用了
 * @author kong
 * @date 2021/07/19 21:06
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
public class RedisRepository {
    /**
     * Spring Redis Template
     */
    private  final RedisTemplate<String, Object> redisTemplate;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
