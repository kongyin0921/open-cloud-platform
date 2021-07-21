package com.ocp.common.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 *  xxl-job配置
 * @author kong
 * @date 2021/07/21 21:45
 * blog: http://blog.kongyin.ltd
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    @NestedConfigurationProperty
    private XxlAdminProperties admin = new XxlAdminProperties();

    @NestedConfigurationProperty
    private XxlExecutorProperties executor = new XxlExecutorProperties();

}
