package com.ocp.common.zookeeper;

import com.ocp.common.zookeeper.properties.ZookeeperProperty;
import com.ulisesbocchio.jasyptspringboot.annotation.ConditionalOnMissingBean;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * zookeeper 配置类
 *
 * @author kong
 * @date 2021/07/16 22:00
 * blog: http://blog.kongyin.ltd
 */
@EnableConfigurationProperties(ZookeeperProperty.class)
@ComponentScan
public class ZookeeperAutoConfiguration {

    @Bean(initMethod = "start",destroyMethod = "close")
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework(ZookeeperProperty property){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(property.getBaseSleepTime(), property.getMaxRetries());
        return CuratorFrameworkFactory.builder().connectString(property.getConnectString())
                .connectionTimeoutMs(property.getConnectionTimeout())
                .sessionTimeoutMs(property.getSessionTimeout())
                .retryPolicy(retryPolicy).build();
    }
}
