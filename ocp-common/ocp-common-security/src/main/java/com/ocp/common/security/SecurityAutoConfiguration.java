package com.ocp.common.security;

import com.ocp.common.security.porperties.SecurityProperties;
import com.ocp.common.security.porperties.TokenStoreProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

/**
 * 安全自动配置
 * @author kong
 * @date 2021/07/28 20:59
 * blog: http://blog.kongyin.ltd
 */
@ComponentScan
@EnableConfigurationProperties({SecurityProperties.class, TokenStoreProperties.class})
public class SecurityAutoConfiguration {
}
