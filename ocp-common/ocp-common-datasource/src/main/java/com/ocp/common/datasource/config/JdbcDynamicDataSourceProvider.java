package com.ocp.common.datasource.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.ocp.common.datasource.properties.DataSourceProperties;
import com.ocp.common.datasource.support.DataSourceConstants;
import org.jasypt.encryption.StringEncryptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kong
 * @date 2021/07/14 21:48
 * blog: http://blog.kongyin.ltd
 */
public class JdbcDynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {

    private final DataSourceProperties properties;

    private final StringEncryptor stringEncryptor;

    public JdbcDynamicDataSourceProvider(StringEncryptor stringEncryptor,DataSourceProperties properties) {
        super(properties.getUrl(), properties.getUsername(), properties.getPassword());
        this.properties = properties;
        this.stringEncryptor = stringEncryptor;
    }
    /**
     * 执行语句获得数据源参数
     * @param statement 语句
     * @return 数据源参数
     * @throws SQLException sql异常
     */
    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {


        Map<String,DataSourceProperty> map = new HashMap<>(8);
        //添加主数据源
        DataSourceProperty property = new DataSourceProperty();
        property.setDriverClassName(DataSourceConstants.DS_DRIVER);
        property.setUsername(properties.getUsername());
        property.setPassword(properties.getPassword());
        property.setLazy(true);

        map.put(DataSourceConstants.DS_MASTER,property);
        //添加从数据源
        ResultSet rs = statement.executeQuery(properties.getQueryDsSql());
        while (rs.next()){
            String name = rs.getString(DataSourceConstants.DS_NAME);
            String username = rs.getString(DataSourceConstants.DS_USER_NAME);
            String password = rs.getString(DataSourceConstants.DS_USER_PWD);
            String url = rs.getString(DataSourceConstants.DS_JDBC_URL);
            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            dataSourceProperty.setDriverClassName(DataSourceConstants.DS_DRIVER);
            dataSourceProperty.setUsername(username);
            dataSourceProperty.setLazy(true);
            dataSourceProperty.setPassword(stringEncryptor.decrypt(password));
            dataSourceProperty.setUrl(url);
            map.put(name,dataSourceProperty);
        }
        return map;
    }
}
