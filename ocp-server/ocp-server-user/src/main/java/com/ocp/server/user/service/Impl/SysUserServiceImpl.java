package com.ocp.server.user.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocp.common.bean.AbstractEntity;
import com.ocp.common.bean.AbstractServiceImpl;
import com.ocp.common.code.UserType;
import com.ocp.common.constant.CommonConstant;
import com.ocp.common.entity.SysMenu;
import com.ocp.common.entity.SysRole;
import com.ocp.common.entity.SysUser;
import com.ocp.common.exception.ServiceException;
import com.ocp.common.lock.DistributedLock;
import com.ocp.common.security.userdetails.LoginUser;
import com.ocp.server.user.entity.SysRoleUser;
import com.ocp.server.user.entity.SysUserExcel;
import com.ocp.server.user.mapper.SysRoleMenuMapper;
import com.ocp.server.user.mapper.SysUserMapper;
import com.ocp.server.user.service.ISysRoleUserService;
import com.ocp.server.user.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kong
 * @date 2021/08/21 21:22
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Service
public class SysUserServiceImpl extends AbstractServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final static String LOCK_KEY_USERNAME = "username:";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private ISysRoleUserService roleUserService;

    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private DistributedLock lock;

    @Override
    public LoginUser findByUsername(String username) {
        SysUser sysUser = this.selectByUsername(username);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginUser findByOpenId(String openId) {
        SysUser sysUser = this.selectByOpenId(openId);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginUser findByMobile(String mobile) {
        SysUser sysUser = this.selectByMobile(mobile);
        return getLoginAppUser(sysUser);
    }

    @Override
    public LoginUser getLoginAppUser(SysUser sysUser) {
        if (sysUser != null) {
            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(sysUser, loginUser);

            List<SysRole> sysRoles = roleUserService.findRolesByUserId(sysUser.getId());
            // ????????????
            loginUser.setRoles(sysRoles);

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<String> roleIds = sysRoles.parallelStream().map(AbstractEntity::getId).collect(Collectors.toSet());
                List<SysMenu> menus = roleMenuMapper.findMenusByRoleIds(roleIds, CommonConstant.PERMISSION);
                if (!CollectionUtils.isEmpty(menus)) {
                    Set<String> permissions = menus.parallelStream().map(p -> p.getPath())
                            .collect(Collectors.toSet());
                    // ??????????????????
                    loginUser.setPermissions(permissions);
                }
            }
            return loginUser;
        }
        return null;
    }

    /**
     * ???????????????????????????
     * @param username
     * @return
     */
    @Override
    public SysUser selectByUsername(String username) {
        List<SysUser> users = baseMapper.selectList(
                new QueryWrapper<SysUser>().eq("username", username)
        );
        return getUser(users);
    }

    /**
     * ???????????????????????????
     * @param mobile
     * @return
     */
    @Override
    public SysUser selectByMobile(String mobile) {
        List<SysUser> users = baseMapper.selectList(
                new QueryWrapper<SysUser>().eq("mobile", mobile)
        );
        return getUser(users);
    }

    /**
     * ??????openId????????????
     * @param openId
     * @return
     */
    @Override
    public SysUser selectByOpenId(String openId) {
        List<SysUser> users = baseMapper.selectList(
                new QueryWrapper<SysUser>().eq("open_id", openId)
        );
        return getUser(users);
    }

    private SysUser getUser(List<SysUser> users) {
        SysUser user = null;
        if (users != null && !users.isEmpty()) {
            user = users.get(0);
        }
        return user;
    }

    /**
     * ?????????????????????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setRoleToUser(String id, Set<String> roleIds) {
        SysUser sysUser = baseMapper.selectById(id);
        if (sysUser == null) {
            throw new IllegalArgumentException("???????????????");
        }

        roleUserService.deleteUserRole(id, null);
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<SysRoleUser> roleUsers = new ArrayList<>(roleIds.size());
            roleIds.forEach(roleId -> roleUsers.add(new SysRoleUser(id, roleId)));
            roleUserService.saveBatch(roleUsers);
        }
    }

    @Transactional
    @Override
    public void updatePassword(String id, String oldPassword, String newPassword) {
        SysUser sysUser = baseMapper.selectById(id);
        if (StrUtil.isNotBlank(oldPassword)) {
            if (!passwordEncoder.matches(oldPassword, sysUser.getPassword())) {
                throw new ServiceException("???????????????");
            }
        }
        if (StrUtil.isBlank(newPassword)) {
            newPassword = CommonConstant.DEF_USER_PASSWORD;
        }
        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        baseMapper.updateById(user);
    }

    /*@Override
    public PageResult<SysUser> findUsers(Map<String, Object> params) {
        Page<SysUser> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<SysUser> list = baseMapper.findList(page, params);
        long total = page.getTotal();
        if (total > 0) {
            List<Long> userIds = list.stream().map(SysUser::getId).collect(Collectors.toList());

            List<SysRole> sysRoles = roleUserService.findRolesByUserIds(userIds);
            list.forEach(u -> u.setRoles(sysRoles.stream().filter(r -> !ObjectUtils.notEqual(u.getId(), r.getUserId()))
                    .collect(Collectors.toList())));
        }
        return PageResult.<SysUser>builder().data(list).code(0).count(total).build();
    }*/

    @Override
    public List<SysRole> findRolesByUserId(String userId) {
        return roleUserService.findRolesByUserId(userId);
    }

    @Override
    public void updateEnabled(Map<String, Object> params) {
        Long id = MapUtils.getLong(params, "id");
        Boolean enabled = MapUtils.getBoolean(params, "enabled");

        SysUser appUser = baseMapper.selectById(id);
        if (appUser == null) {
            throw new ServiceException("???????????????");
        }
        appUser.setEnabled(enabled);
        appUser.setUpdateTime(new Date());

        int i = baseMapper.updateById(appUser);
        log.info("???????????????{}", appUser);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateUser(SysUser sysUser) throws Exception {
        if (sysUser.getId() == null) {
            if (StringUtils.isBlank(sysUser.getType())) {
                sysUser.setType(UserType.BACKEND.name());
            }
            sysUser.setPassword(passwordEncoder.encode(CommonConstant.DEF_USER_PASSWORD));
            sysUser.setEnabled(Boolean.TRUE);
        }
        String username = sysUser.getUsername();
        boolean result = super.saveOrUpdateIdempotency(sysUser, lock
                , LOCK_KEY_USERNAME+username, new QueryWrapper<SysUser>().eq("username", username)
                , username+"?????????");
        //????????????
        if (result && StrUtil.isNotEmpty(sysUser.getRoleId())) {
            roleUserService.deleteUserRole(sysUser.getId(), null);
            List roleIds = Arrays.asList(sysUser.getRoleId().split(","));
            if (!CollectionUtils.isEmpty(roleIds)) {
                List<SysRoleUser> roleUsers = new ArrayList<>(roleIds.size());
                roleIds.forEach(roleId -> roleUsers.add(new SysRoleUser(sysUser.getId(), roleId.toString())));
                roleUserService.saveBatch(roleUsers);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delUser(String id) {
        roleUserService.deleteUserRole(id, null);
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public List<SysUserExcel> findAllUsers(Map<String, Object> params) {
        List<SysUserExcel> sysUserExcels = new ArrayList<>();
        List<SysUser> list = baseMapper.findList(new Page<>(1, -1), params);

        for (SysUser sysUser : list) {
            SysUserExcel sysUserExcel = new SysUserExcel();
            BeanUtils.copyProperties(sysUser, sysUserExcel);
            sysUserExcels.add(sysUserExcel);
        }
        return sysUserExcels;
    }
}
