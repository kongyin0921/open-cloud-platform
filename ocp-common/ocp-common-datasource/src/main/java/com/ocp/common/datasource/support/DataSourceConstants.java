package com.ocp.common.datasource.support;

/**
 * 数据源相关常量
 * @author kong
 * @date 2021/07/14 21:57
 * blog: http://blog.kongyin.ltd
 */
public interface DataSourceConstants {

    /**
     * 数据源名称
     */
    String DS_NAME = "name";

    /**
     * 默认驱动
     */
    String DS_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * 默认数据源（master）
     */
    String DS_MASTER = "master";

    /**
     * jdbcurl
     */
    String DS_JDBC_URL = "url";

    /**
     * 用户名
     */
    String DS_USER_NAME = "username";

    /**
     * 密码
     */
    String DS_USER_PWD = "password";
}
