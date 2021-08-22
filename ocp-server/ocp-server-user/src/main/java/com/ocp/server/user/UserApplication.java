package com.ocp.server.user;

import com.ocp.feign.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 用户服务
 * @author kong
 * @date 2021/08/21 19:24
 * blog: http://blog.kongyin.ltd
 */
@EnableTransactionManagement
@EnableFeignInterceptor
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
