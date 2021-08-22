package com.ocp.server.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocp.common.entity.SysRole;
import com.ocp.common.mybatis.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 角色
 * @author kong
 * @date 2021/08/21 20:53
 * blog: http://blog.kongyin.ltd
 */
@Mapper
public interface SysRoleMapper extends IBaseMapper<SysRole> {
    List<SysRole> findList(Page<SysRole> page, @Param("r") Map<String, Object> params);

    List<SysRole> findAll();
}
