package com.ocp.auth.config;

import com.ocp.common.config.DefaultPasswordConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author kong
 * @date 2021/08/12 22:39
 * blog: http://blog.kongyin.ltd
 */
@Configuration
@Import(DefaultPasswordConfig.class)
public class SecurityConfig {
}
