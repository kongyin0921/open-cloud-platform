package com.ocp.server.user.service;


import com.ocp.common.bean.IBaseService;
import com.ocp.common.entity.SysRole;
import com.ocp.server.user.entity.SysRoleUser;

import java.util.List;


public interface ISysRoleUserService extends IBaseService<SysRoleUser> {
	int deleteUserRole(String userId, String roleId);

	int saveUserRoles(String userId, String roleId);

	/**
	 * 根据用户id获取角色
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> findRolesByUserId(String userId);

	/**
	 * 根据用户ids 获取
	 *
	 * @param userIds
	 * @return
	 */
	List<SysRole> findRolesByUserIds(List<Long> userIds);
}
