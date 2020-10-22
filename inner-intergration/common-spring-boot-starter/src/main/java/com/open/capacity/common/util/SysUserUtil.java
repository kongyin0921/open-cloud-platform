package com.open.capacity.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.open.capacity.common.auth.details.LoginAppUser;
import com.open.capacity.common.model.SysRole;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName SysUserUtil
 * @Description 获取用户
 * @Auther kongyin
 * @Date 2020/10/22 14:54
 **/
public class SysUserUtil {

    public static LoginAppUser getLoginAppUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof  OAuth2Authentication){
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            Authentication userAuthentication = oAuth2Auth.getUserAuthentication();
            if(userAuthentication instanceof UsernamePasswordAuthenticationToken){
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) userAuthentication;
                if(authentication.getPrincipal() instanceof LoginAppUser){
                    return (LoginAppUser) authenticationToken.getPrincipal();
                }else if (authentication.getPrincipal() instanceof Map){
                    LoginAppUser loginAppUser = BeanUtil.mapToBean((Map) authenticationToken.getPrincipal(), LoginAppUser.class, true);
                    Set<SysRole> roles = new HashSet<>();
                    if (CollectionUtil.isNotEmpty(loginAppUser.getSysRoles())) {
                        for(Iterator<SysRole> it = loginAppUser.getSysRoles().iterator(); it.hasNext();){
                            SysRole role =  BeanUtil.mapToBean((Map) it.next() , SysRole.class, false);
                            roles.add(role) ;
                        }
                    }
                    loginAppUser.setSysRoles(roles);
                    return loginAppUser;
                }
            }else if(authentication instanceof PreAuthenticatedAuthenticationToken){
                PreAuthenticatedAuthenticationToken authenticationToken =(PreAuthenticatedAuthenticationToken) userAuthentication;
                return (LoginAppUser) authenticationToken.getPrincipal();
            }
        }

        String token = TokenUtil.getToken();
        return null;
    }
}
