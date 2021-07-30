package com.ocp.common.security.config.store;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 认证服务器使用 JWT RSA 非对称加密令牌
 * @author kong
 * @date 2021/07/30 22:53
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@ConditionalOnProperty(prefix = "ocp.oauth2.token.store", name = "type", havingValue = "authJwt")
public class AuthJwtTokenStore {
}
