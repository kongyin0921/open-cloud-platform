package com.ocp.common.security.porperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Token配置
 * @author kong
 * @date 2021/07/29 21:48
 * blog: http://blog.kongyin.ltd
 */
@Data
@ConfigurationProperties(prefix = "ocp.oauth2.token.store")
public class TokenStoreProperties {
    /**
     * token存储类型(redis/db/authJwt/resJwt)
     */
    private String type = "redis";
}
