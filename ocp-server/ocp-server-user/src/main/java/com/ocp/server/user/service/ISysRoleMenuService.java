package com.ocp.server.user.service;


import com.ocp.common.bean.IBaseService;
import com.ocp.common.entity.SysMenu;
import com.ocp.server.user.entity.SysRoleMenu;

import java.util.List;
import java.util.Set;


public interface ISysRoleMenuService extends IBaseService<SysRoleMenu> {
	int save(Long roleId, Long menuId);

	int delete(Long roleId, Long menuId);

	List<SysMenu> findMenusByRoleIds(Set<Long> roleIds, Integer type);

	List<SysMenu> findMenusByRoleCodes(Set<String> roleCodes, Integer type);
}
