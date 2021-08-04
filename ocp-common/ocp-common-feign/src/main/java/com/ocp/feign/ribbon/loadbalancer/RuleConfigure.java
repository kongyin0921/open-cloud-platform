package com.ocp.feign.ribbon.loadbalancer;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.loadbalancer.IRule;
import com.ocp.feign.ribbon.loadbalancer.rule.VersionIsolationRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author kong
 * @date 2021/08/04 22:23
 * blog: http://blog.kongyin.ltd
 */
public class RuleConfigure {
    @Bean
    @ConditionalOnClass(NacosServer.class)
    @ConditionalOnMissingBean
    public IRule versionIsolationRule() {
        return new VersionIsolationRule();
    }
}
