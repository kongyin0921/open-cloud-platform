package com.ocp.feign.ribbon;

import com.ocp.common.constant.ConfigConstants;
import com.ocp.feign.ribbon.loadbalancer.RuleConfigure;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * 自定义负载均衡配置
 * @author kong
 * @date 2021/08/04 22:48
 * blog: http://blog.kongyin.ltd
 */
@ConditionalOnProperty(value = ConfigConstants.CONFIG_RIBBON_ISOLATION_ENABLED, havingValue = "true")
@RibbonClients(defaultConfiguration = {RuleConfigure.class})
public class LbIsolationAutoConfigure {
}
