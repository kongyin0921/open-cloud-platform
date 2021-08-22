package com.ocp.server.user.mapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocp.common.entity.SysUser;
import com.ocp.common.mybatis.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户表 Mapper 接口
 *
 */
@Mapper
public interface SysUserMapper extends IBaseMapper<SysUser> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<SysUser> findList(Page<SysUser> page, @Param("u") Map<String, Object> params);
}
