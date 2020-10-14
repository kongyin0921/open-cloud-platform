package com.ycz.platform.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.ycz.platform.datasource.aop.DataSourceAOP;
import com.ycz.platform.datasource.constant.DataSourceKey;
import com.ycz.platform.datasource.util.DynamicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 在设置了spring.datasource.enable.dynamic 等于true是开启多数据源，配合日志
 * @author kong
 * @date 2020/10/13
 **/
@Configuration
@Import(DataSourceAOP.class)
@AutoConfigureBefore(value={DruidDataSourceAutoConfigure.class, MybatisPlusAutoConfiguration.class})
@ConditionalOnProperty(name = {"spring.datasource.dynamic.enable"}, matchIfMissing = false, havingValue = "true")
public class DataSourceAutoConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.core")
    public DataSource dataSourceCore(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.log")
    public DataSource dataSourceLog(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource dataSource(){
        DynamicDataSource dataSource = new DynamicDataSource();
        DataSource dataSourceCore = dataSourceCore();
        dataSource.addDataSource(DataSourceKey.core,dataSourceCore);
        dataSource.addDataSource(DataSourceKey.log,dataSourceLog());
        dataSource.setDefaultTargetDataSource(dataSourceCore);
        return dataSource;
    }

    /**
     * 将数据源纳入spring事物管理
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
