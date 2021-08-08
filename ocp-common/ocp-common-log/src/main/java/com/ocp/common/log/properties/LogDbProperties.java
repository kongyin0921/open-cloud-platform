package com.ocp.common.log.properties;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志数据源配置
 * logType=db时生效(非必须)，如果不配置则使用当前数据源
 * @author kong
 * @date 2021/08/08 12:20
 * blog: http://blog.kongyin.ltd
 */
@Data
@ConfigurationProperties(prefix = "ocp.audit-log.datasource")
public class LogDbProperties extends HikariConfig {
}
