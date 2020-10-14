package com.ycz.platform.datasource.util;

import com.ycz.platform.datasource.constant.DataSourceKey;

/**
 * @ClassName DataSourceHolder
 * @Description 用于数据源切换
 * @Author kongyin
 * @Date 2020/10/14 9:35
 **/
public class DataSourceHolder {
    /**
     * 注意使用ThreadLocal，微服务下游建议使用信号量
     */
    private static final ThreadLocal<DataSourceKey> DATA_SOURCE_KEY_THREAD_LOCAL = new ThreadLocal<>();
    /**
     * 得到当前的数据库连接
     */
    public static DataSourceKey getDataSourceKey() {
        return DATA_SOURCE_KEY_THREAD_LOCAL.get();
    }
    /**
     * 设置当前的数据库连接
     */
    public static void setDataSourceKey(DataSourceKey dataSourceKey){
        DATA_SOURCE_KEY_THREAD_LOCAL.set(dataSourceKey);
    }
    /**
     * 清除当前的数据库连接
     */
    public static void clearDataSourceKey(){
        DATA_SOURCE_KEY_THREAD_LOCAL.remove();
    };
}
