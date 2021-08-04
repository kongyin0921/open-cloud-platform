package com.ocp.feign.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @author kong
 * @date 2021/08/04 22:16
 * blog: http://blog.kongyin.ltd
 */
@ConditionalOnProperty(prefix = "ocp.rest-template")
public class RestTemplateProperties {

    /**
     * 最大链接数
     */
    private int maxTotal = 200;
    /**
     * 同路由最大并发数
     */
    private int maxPerRoute = 50;
    /**
     * 读取超时时间 ms
     */
    private int readTimeout = 35000;
    /**
     * 链接超时时间 ms
     */
    private int connectTimeout = 10000;
}
