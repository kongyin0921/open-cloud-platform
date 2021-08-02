/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos;

import cn.hutool.system.SystemUtil;

import com.alibaba.nacos.console.config.ConfigConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Nacos starter.
 * 源码运行，生产建议去官网下载
 * @author nacos
 */
@SpringBootApplication(scanBasePackages = "com.alibaba.nacos")
@ServletComponentScan
@EnableScheduling
@Slf4j
public class NacosApp {

    public static void main(String[] args) {
        //初始化
        initEnv();
        SpringApplication.run(NacosApp.class, args);
    }

    /**
     * 初始化运行环境
     */
    private static void initEnv() {

        // 设置 nacos 运行文件存放位置
        System.setProperty(SystemUtil.USER_HOME, "./nacos_work");
        // 设置单机模式
        System.setProperty(ConfigConstants.STANDALONE_MODE, "true");
        // 是否开启认证
        System.setProperty(ConfigConstants.AUTH_ENABLED, "false");
        // 日志存放位置
        System.setProperty(ConfigConstants.LOG_BASEDIR, "./logs");
    }
}

