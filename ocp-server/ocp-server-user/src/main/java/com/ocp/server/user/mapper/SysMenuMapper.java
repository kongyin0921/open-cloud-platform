package com.ocp.server.user.mapper;

import com.ocp.common.entity.SysMenu;
import com.ocp.common.mybatis.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 菜单
 * @author kong
 * @date 2021/08/21 20:40
 * blog: http://blog.kongyin.ltd
 */
@Mapper
public interface SysMenuMapper extends IBaseMapper<SysMenu> {
}
