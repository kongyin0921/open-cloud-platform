package com.ocp.common.security.config.store;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

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
}
