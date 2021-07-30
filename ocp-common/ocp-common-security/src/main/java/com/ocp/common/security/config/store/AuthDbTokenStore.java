package com.ocp.common.security.config.store;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 认证服务器使用数据库存取令牌
 * @author kong
 * @date 2021/07/30 22:51
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@ConditionalOnProperty(prefix = "ocp.oauth2.token.store", name = "type", havingValue = "db")
public class AuthDbTokenStore {
}
