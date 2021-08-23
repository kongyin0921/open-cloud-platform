package com.ocp.server.user.service;


import com.ocp.common.bean.IBaseService;
import com.ocp.common.entity.SysRole;
import com.ocp.common.entity.SysUser;
import com.ocp.common.security.userdetails.LoginUser;
import com.ocp.server.user.entity.SysUserExcel;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface ISysUserService extends IBaseService<SysUser> {
	/**
	 * 获取UserDetails对象
	 * @param username
	 * @return
	 */
	LoginUser findByUsername(String username);

	LoginUser findByOpenId(String openId);

	LoginUser findByMobile(String mobile);

	/**
	 * 通过SysUser 转换为 LoginAppUser，把roles和permissions也查询出来
	 * @param sysUser
	 * @return
	 */
	LoginUser getLoginAppUser(SysUser sysUser);

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	SysUser selectByUsername(String username);
	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	SysUser selectByMobile(String mobile);
	/**
	 * 根据openId查询用户
	 * @param openId
	 * @return
	 */
	SysUser selectByOpenId(String openId);

	/**
	 * 用户分配角色
	 * @param id
	 * @param roleIds
	 */
	void setRoleToUser(String id, Set<String> roleIds);

	/**
	 * 更新密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	void updatePassword(String id, String oldPassword, String newPassword);

	/**
	 * 用户列表
	 * @param params
	 * @return
	 */
	//PageResult<SysUser> findUsers(Map<String, Object> params);


	/**
	 * 用户角色列表
	 * @param userId
	 * @return
	 */
	List<SysRole> findRolesByUserId(String userId);

	/**
	 * 状态变更
	 * @param params
	 * @return
	 */
	void updateEnabled(Map<String, Object> params);

	/**
	 * 查询全部用户
	 * @param params
	 * @return
	 */
	List<SysUserExcel> findAllUsers(Map<String, Object> params);

	void saveOrUpdateUser(SysUser sysUser) throws Exception;

	/**
	 * 删除用户
	 */
	boolean delUser(String id);
}
