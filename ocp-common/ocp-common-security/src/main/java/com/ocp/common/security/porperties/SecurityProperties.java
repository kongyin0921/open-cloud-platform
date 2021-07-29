package com.ocp.common.security.porperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author kong
 * @date 2021/07/29 21:43
 * blog: http://blog.kongyin.ltd
 */
@Data
@ConfigurationProperties(prefix = "ocp.security")
@RefreshScope
public class SecurityProperties {
    /**
     * 身份验证属性
     */
    private AuthProperties auth = new AuthProperties();
    /**
     *配置需要放权的url白名单
     */
    private PermitProperties ignore = new PermitProperties();
}
