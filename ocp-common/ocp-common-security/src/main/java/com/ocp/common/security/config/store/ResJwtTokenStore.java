package com.ocp.common.security.config.store;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 资源服务器 TokenStore 配置类，使用 JWT RSA 非对称加密
 * @author kong
 * @date 2021/07/30 22:55
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@ConditionalOnProperty(prefix = "ocp.oauth2.token.store", name = "type", havingValue = "resJwt")
public class ResJwtTokenStore {
}
