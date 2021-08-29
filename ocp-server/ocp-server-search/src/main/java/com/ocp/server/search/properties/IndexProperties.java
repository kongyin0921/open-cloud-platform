package com.ocp.server.search.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author kong
 * @date 2021/08/28 15:36
 * blog: http://blog.kongyin.ltd
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "ocp.indices")
public class IndexProperties {
    /**
     * 配置过滤的索引名：默认只显示这些索引
     */
    private String show;
}
