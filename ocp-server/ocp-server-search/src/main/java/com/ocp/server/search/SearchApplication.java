package com.ocp.server.search;

import com.ocp.server.search.properties.IndexProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author kong
 * @date 2021/08/28 15:14
 * blog: http://blog.kongyin.ltd
 */
@EnableConfigurationProperties(IndexProperties.class)
@EnableFeignClients(basePackages = {"com.ocp"})
@EnableDiscoveryClient
@SpringBootApplication
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }
}
