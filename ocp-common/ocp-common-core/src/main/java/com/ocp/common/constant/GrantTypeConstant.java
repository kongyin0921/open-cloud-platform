package com.ocp.common.constant;

/**
 * 认证类型常量类
 * @author kong
 * @date 2021/08/08 21:50
 * blog: http://blog.kongyin.ltd
 */
public interface GrantTypeConstant {

    /**
     * 刷新模式
     */
    String REFRESH_TOKEN = "refresh_token";
    /**
     * 授权码模式
     */
    String AUTHORIZATION_CODE = "authorization_code";
    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";
    /**
     * 密码模式
     */
    String PASSWORD = "password";
    /**
     * 简化模式
     */
    String IMPLICIT = "implicit";
    /**
     * 自定义：手机 + 密码
     */
    String MOBILE_PASSWORD = "mobile_password";
    /**
     * 自定义：OPENID
     */
    String OPENID = "openId";
    /**
     * 自定义：账号密码 + 验证码
     */
    String PASSWORD_CODE = "password_code";
    /**
     * 自定义：微信小程序
     */
    String WX_MINI_PROGRAM = "wx_mini_program";
}
