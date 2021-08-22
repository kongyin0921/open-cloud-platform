package com.ocp.server.user.service;


import cn.hutool.db.PageResult;
import com.ocp.common.bean.IBaseService;
import com.ocp.common.bean.Result;
import com.ocp.common.entity.SysRole;

import java.util.List;
import java.util.Map;


public interface ISysRoleService extends IBaseService<SysRole> {
	void saveRole(SysRole sysRole) throws Exception;

	void deleteRole(Long id);

	/**
	 * 角色列表
	 * @param params
	 * @return
	 */
	PageResult<SysRole> findRoles(Map<String, Object> params);

	/**
	 * 新增或更新角色
	 * @param sysRole
	 * @return Result
	 */
	Result saveOrUpdateRole(SysRole sysRole) throws Exception;

	/**
	 * 查询所有角色
	 * @return
	 */
	List<SysRole> findAll();
}
