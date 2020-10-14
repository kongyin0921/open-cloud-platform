package com.ycz.platform.datasource.util;

import com.ycz.platform.datasource.constant.DataSourceKey;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DynamicDataSource
 * @Description spring动态数据源（需要继承AbstractRoutingDataSource）
 * @Author kongyin
 * @Date 2020/10/14 9:28
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> datasources;

    public DynamicDataSource() {
        datasources = new HashMap<>();
        super.setTargetDataSources(datasources);
    }

    /**
     * 添加数据源
     * @param key
     * @param data
     * @param <T>
     */
    public <T extends DataSource> void addDataSource(DataSourceKey key, T data) {
        datasources.put(key, data);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSourceKey();
    }
}
