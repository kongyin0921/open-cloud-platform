package com.ocp.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信 配置
 * @author kong
 * @date 2021/08/19 21:15
 * blog: http://blog.kongyin.ltd
 */
@Data
@ConfigurationProperties(prefix = "wx")
public class WxMiniProgramProperties {

    /**
     * 小程序appId
     */
    private String appId;
    /**
     * 小程序appSecret
     */
    private String appSecret;

}
