package com.ocp.gateway;

import com.ocp.feign.annotation.EnableBaseFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关服务
 * @author kong
 * @date 2021/08/20 20:22
 * blog: http://blog.kongyin.ltd
 */
@EnableFeignClients(basePackages = {"com.ocp"})
@EnableBaseFeignInterceptor
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
