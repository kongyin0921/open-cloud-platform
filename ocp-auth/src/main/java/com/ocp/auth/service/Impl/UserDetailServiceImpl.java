package com.ocp.auth.service.Impl;

import com.ocp.auth.service.AuthUserDetailsService;
import com.ocp.common.constant.SecurityConstants;
import com.ocp.common.feign.UserService;
import com.ocp.common.security.userdetails.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kong
 * @date 2021/08/15 16:00
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements AuthUserDetailsService {

    private static final String ACCOUNT_TYPE = SecurityConstants.DEF_ACCOUNT_TYPE;

    @Resource
    private UserService userService;

    @Override
    public boolean supports(String accountType) {
        return ACCOUNT_TYPE.equals(accountType);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LoginUser loginUser = userService.findByUsername(username);
        if (loginUser == null) {
            throw new InternalAuthenticationServiceException("用户名或密码错误");
        }
        return checkUser(loginUser);
    }

    @Override
    public SocialUserDetails loadUserByOpenId(String openId) {
        LoginUser loginUser = userService.findByOpenId(openId);
        return checkUser(loginUser);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        LoginUser loginUser = userService.findByMobile(mobile);
        return checkUser(loginUser);
    }

    private LoginUser checkUser(LoginUser loginUser) {
        if (loginUser != null && !loginUser.isEnabled()) {
            throw new DisabledException("用户已作废");
        }
        return loginUser;
    }
}
