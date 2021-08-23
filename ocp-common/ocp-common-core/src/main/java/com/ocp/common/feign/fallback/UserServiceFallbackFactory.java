package com.ocp.common.feign.fallback;

import com.ocp.common.feign.UserService;
import com.ocp.common.entity.SysUser;
import com.ocp.common.security.userdetails.LoginUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kong
 * @date 2021/08/07 18:29
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
public class UserServiceFallbackFactory  implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public SysUser selectByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return new SysUser();
            }

            @Override
            public LoginUser findByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return new LoginUser();
            }

            @Override
            public LoginUser findByMobile(String mobile) {
                log.error("通过手机号查询用户异常:{}", mobile, throwable);
                return new LoginUser();
            }

            @Override
            public LoginUser findByOpenId(String openId) {
                log.error("通过openId查询用户异常:{}", openId, throwable);
                return new LoginUser();
            }
        };
    }
}
