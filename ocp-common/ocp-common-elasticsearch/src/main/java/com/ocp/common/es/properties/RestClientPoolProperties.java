package com.ocp.common.es.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * es的httpClient连接池配置
 * @author kong
 * @date 2021/07/22 21:38
 * blog: http://blog.kongyin.ltd
 */
@Data
@ConfigurationProperties(prefix = "ocp.elasticsearch.rest-pool")
@RefreshScope
public class RestClientPoolProperties {
    /**
     * 链接建立超时时间
     */
    private Integer connectTimeOut = 1000;
    /**
     * 等待数据超时时间
     */
    private Integer socketTimeOut = 30000;
    /**
     * 连接池获取连接的超时时间
     */
    private Integer connectionRequestTimeOut = 500;
    /**
     * 最大连接数
     */
    private Integer maxConnectNum = 30;
    /**
     * 最大路由连接数
     */
    private Integer maxConnectPerRoute = 10;
}
