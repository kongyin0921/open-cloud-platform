package com.ycz.platform.datasource.aop;

import com.ycz.platform.datasource.annotation.DataSource;
import com.ycz.platform.datasource.constant.DataSourceKey;
import com.ycz.platform.datasource.util.DataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * @ClassName DataSourceAOP
 * @Description 切换数据源Advice
 * @Author kongyin
 * @Date 2020/10/14 10:53
 **/
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAOP {

    @Before("@annotation(dataSource)")
    public void changeDataSource(JoinPoint point, DataSource dataSource){
        String name = dataSource.name();
        try {
            DataSourceKey dataSourceKey = DataSourceKey.valueOf(name);
            DataSourceHolder.setDataSourceKey(dataSourceKey);
        }catch (Exception e){
            log.error("数据源[{}]不存在，使用默认数据源 > {}", name, point.getSignature());
        }
    }

    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint joinPoint,DataSource dataSource){
        log.debug("Revert DataSource : {transIdo} > {}", dataSource.name(), joinPoint.getSignature());
        DataSourceHolder.clearDataSourceKey();
    }
}
