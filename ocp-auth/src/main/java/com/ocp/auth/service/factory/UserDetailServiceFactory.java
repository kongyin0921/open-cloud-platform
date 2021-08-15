package com.ocp.auth.service.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ocp.auth.service.AuthUserDetailsService;
import com.ocp.common.constant.SecurityConstants;
import com.ocp.common.security.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 用户service工厂
 * @author kong
 * @date 2021/08/15 15:28
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Component
public class UserDetailServiceFactory  {

    private static final String ERROR_MSG = "找不到账号类型为 {} 的实现类";

    @Resource
    private List<AuthUserDetailsService> userDetailsServices;

    public AuthUserDetailsService getService(Authentication authentication) {
        String accountType = AuthUtils.getAccountType(authentication);
        return this.getService(accountType);
    }

    public AuthUserDetailsService getService(String accountType) {
        if (StrUtil.isEmpty(accountType)) {
            accountType = SecurityConstants.DEF_ACCOUNT_TYPE;
        }
        log.info("UserDetailServiceFactory.getService:{}", accountType);
        if (CollUtil.isNotEmpty(userDetailsServices)) {
            for (AuthUserDetailsService userService : userDetailsServices) {
                if (userService.supports(accountType)) {
                    return userService;
                }
            }
        }
        throw new InternalAuthenticationServiceException(StrUtil.format(ERROR_MSG, accountType));
    }
}
