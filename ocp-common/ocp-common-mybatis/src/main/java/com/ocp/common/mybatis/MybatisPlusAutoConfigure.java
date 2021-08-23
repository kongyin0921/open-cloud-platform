package com.ocp.common.mybatis;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.ocp.common.context.ContextUserDetails;
import com.ocp.common.context.UserDetailsContextHolder;
import com.ocp.common.mybatis.config.AbstractBaseMetaObjectHandler;
import com.ocp.common.mybatis.properties.MybatisPlusAutoFillProperties;
import com.ocp.common.mybatis.resolver.SqlFilterArgumentResolver;
import com.ocp.common.properties.TenantProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * mybatis plus 统一配置
 * @author kong
 * @date 2021/07/15 20:27
 * blog: http://blog.kongyin.ltd
 */
@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties(MybatisPlusAutoFillProperties.class)
public class MybatisPlusAutoConfigure implements WebMvcConfigurer {

    /**
     * 默认操作用户ID
     */
    private final String DEFAULT_OPERATION_USER_ID = "111111";

    @Autowired
    private TenantHandler tenantHandler;

    @Autowired
    private ISqlParserFilter sqlParserFilter;

    @Autowired
    private TenantProperties tenantProperties;

    @Autowired
    private MybatisPlusAutoFillProperties autoFillProperties;
    /**
     * SQL 过滤器避免SQL 注入
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SqlFilterArgumentResolver());
    }



    /**
     * 分页插件, 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
     */
    /*@Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }*/

    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        boolean enableTenant = tenantProperties.getEnable();
        //是否开启多租户隔离
        if (enableTenant) {
            TenantSqlParser tenantSqlParser = new TenantSqlParser()
                    .setTenantHandler(tenantHandler);
            paginationInterceptor.setSqlParserList(CollUtil.toList(tenantSqlParser));
            paginationInterceptor.setSqlParserFilter(sqlParserFilter);
        }
        return paginationInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "ocp.mybatis-plus.auto-fill", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MetaObjectHandler metaObjectHandler() {
        return new AbstractBaseMetaObjectHandler(autoFillProperties) {
            @Override
            public String getOperatorId() {
                ContextUserDetails contextUserDetails = UserDetailsContextHolder.getContextUserDetails(true);
                return contextUserDetails != null ? contextUserDetails.getId() : DEFAULT_OPERATION_USER_ID;
            }
        };
    }
}
