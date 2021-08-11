package com.ocp.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocp.auth.entity.Client;
import com.ocp.common.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author kong
 * @date 2021/08/11 22:12
 * blog: http://blog.kongyin.ltd
 */
@Mapper
public interface ClientMapper extends SuperMapper<Client> {
    List<Client> findList(Page<Client> page, @Param("params") Map<String, Object> params );
}
