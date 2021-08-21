package com.ocp.common.config;

import com.ocp.common.util.PwdEncoderUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密配置类
 * @author kong
 * @date 2021/08/07 19:13
 * blog: http://blog.kongyin.ltd
 */
public abstract class AbstractPasswordConfig {
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder(){
        return PwdEncoderUtil.getDelegatingPasswordEncoder(PwdEncoderUtil.PwdEncoderType.BCRYPT);
    }
}
