package com.ocp.common.security.config.store;

import com.ocp.common.security.porperties.SecurityProperties;
import com.ocp.common.security.store.CustomRedisTokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证服务器使用Redis存取令牌
 * 注意: 需要配置redis参数
 * @author kong
 * @date 2021/07/30 22:30
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@ConditionalOnProperty(prefix = "ocp.oauth2.token.store", name = "type", havingValue = "redis", matchIfMissing = true)
public class AuthRedisTokenStore {
    @Bean
    public TokenStore tokenStore(RedisConnectionFactory connectionFactory, SecurityProperties securityProperties, RedisSerializer<Object> redisValueSerializer) {
        return new CustomRedisTokenStore(connectionFactory, securityProperties, redisValueSerializer);
    }
}
