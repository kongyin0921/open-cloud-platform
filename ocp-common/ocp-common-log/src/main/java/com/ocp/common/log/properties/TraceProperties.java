package com.ocp.common.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 日志链路追踪配置
 * @author kong
 * @date 2021/08/08 12:21
 * blog: http://blog.kongyin.ltd
 */
@Data
@ConfigurationProperties(prefix = "ocp.trace")
@RefreshScope
public class TraceProperties {
    /**
     * 是否开启日志链路追踪
     */
    private Boolean enable = false;
}
