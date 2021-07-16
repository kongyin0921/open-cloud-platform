package com.ocp.common.zookeeper;

import com.ocp.common.zookeeper.properties.ZookeeperProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author kong
 * @date 2021/07/16 22:00
 * blog: http://blog.kongyin.ltd
 */
@EnableConfigurationProperties(ZookeeperProperty.class)
public class ZookeeperAutoConfiguration {
}
