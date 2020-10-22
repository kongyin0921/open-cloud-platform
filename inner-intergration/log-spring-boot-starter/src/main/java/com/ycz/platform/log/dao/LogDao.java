package com.ycz.platform.log.dao;

import com.open.capacity.common.model.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.sql.DataSource;

/**
 * @ClassName LogDao
 * @Description 保存日志
 *  * eureka-server配置不需要datasource,不会装配bean
 * @Auther kongyin
 * @Date 2020/10/22 16:11
 **/
@Mapper
@ConditionalOnBean(DataSource.class)
public interface LogDao {

    @Insert("insert into sys_log(username, module, params, remark, flag, create_time) values(#{username}, #{module}, #{params}, #{remark}, #{flag}, #{createTime})")
    int save(SysLog log);

}
