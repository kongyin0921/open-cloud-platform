package com.ocp.common.annotation;

import java.lang.annotation.*;

/**
 *  请求的方法参数上添加该注解，则注入当前登录账号的应用id
 *  例：public void test(@LoginClient String clientId) //注入webApp
 * @author kong
 * @date 2021/08/07 18:38
 * blog: http://blog.kongyin.ltd
 */
@Target(ElementType.PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginClient {
}
