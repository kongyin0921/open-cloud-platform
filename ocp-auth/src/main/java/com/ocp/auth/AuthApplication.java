package com.ocp.auth;

import com.ocp.feign.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证服务
 * @author kong
 * @date 2021/08/02 22:43
 * blog: http://blog.kongyin.ltd
 */
//指定 feign 扫描 包路径
@EnableFeignClients(basePackages = {"com.ocp"})
@EnableFeignInterceptor
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }
}
