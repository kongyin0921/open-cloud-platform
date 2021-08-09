package com.ocp.common.constant;

/**
 * 端点常量
 * @author kong
 * @date 2021/08/08 21:34
 * blog: http://blog.kongyin.ltd
 */
public interface EndpointConstant {

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/validata/code";

    /**
     * 手机号的处理验证码的url前缀
     */
    String MOBILE_VALIDATE_CODE_URL_PREFIX = "/validata/smsCode";

    /**
     * OAUTH模式登录处理地址
     */
    String OAUTH_LOGIN_PRO_URL = "/user/login";

    /**
     * oauth token
     */
    String OAUTH_TOKEN_URL = "/oauth/token";

    /**
     * 获取授权码地址
     */
    String AUTH_CODE_URL = "/oauth/authorize";

    /**
     * 登出URL
     */
    String LOGOUT_URL = "/oauth/remove/token";

    /**
     * 登录页面
     */
    String LOGIN_PAGE = "/login.html";
    /**
     * 登录失败页面
     */
    String LOGIN_FAILURE_PAGE = LOGIN_PAGE + "?error";
}
