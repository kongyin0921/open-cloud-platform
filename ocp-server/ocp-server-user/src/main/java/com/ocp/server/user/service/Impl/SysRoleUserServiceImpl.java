package com.ocp.server.user.service.Impl;

import com.ocp.common.bean.AbstractServiceImpl;
import com.ocp.common.entity.SysRole;
import com.ocp.server.user.entity.SysRoleUser;
import com.ocp.server.user.mapper.SysUserRoleMapper;
import com.ocp.server.user.service.ISysRoleUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kong
 * @date 2021/08/21 21:37
 * blog: http://blog.kongyin.ltd
 */
@Service
public class SysRoleUserServiceImpl extends AbstractServiceImpl<SysUserRoleMapper,SysRoleUser> implements ISysRoleUserService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteUserRole(String userId, String roleId) {
        return sysUserRoleMapper.deleteUserRole(userId, roleId);
    }

    @Override
    public int saveUserRoles(String userId, String roleId) {
        return sysUserRoleMapper.saveUserRoles(userId, roleId);
    }

    @Override
    public List<SysRole> findRolesByUserId(String userId) {
        return sysUserRoleMapper.findRolesByUserId(userId);
    }

    @Override
    public List<SysRole> findRolesByUserIds(List<Long> userIds) {
        return sysUserRoleMapper.findRolesByUserIds(userIds);
    }
}
