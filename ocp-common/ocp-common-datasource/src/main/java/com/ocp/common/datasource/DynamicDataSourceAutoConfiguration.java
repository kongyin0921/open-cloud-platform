package com.ocp.common.datasource;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.ocp.common.datasource.config.JdbcDynamicDataSourceProvider;
import com.ocp.common.datasource.config.LastParamDsProcessor;
import com.ocp.common.datasource.properties.DataSourceProperties;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 动态数据源自动配置
 * @author kong
 * @date 2021/07/14 20:40
 * blog: http://blog.kongyin.ltd
 */

@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {

    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider(StringEncryptor stringEncryptor,
                                                               DataSourceProperties properties){
        return new JdbcDynamicDataSourceProvider(stringEncryptor,properties);
    }

    @Bean
    public DsProcessor dsProcessor(){
        return new LastParamDsProcessor();
    }
}
