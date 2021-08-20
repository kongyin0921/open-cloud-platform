package com.ocp.auth.service;

import java.util.Map;

/**
 * 微信小程序 api服务
 * @author kong
 * @date 2021/08/19 21:13
 * blog: http://blog.kongyin.ltd
 */
public interface WxMiniProgramApiService {

    /**
     * 微信小程序获取openId和session_key
     * 详情参考=> https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
     *
     * @param code 小程序登录时获取的code
     * @return
     */
    Map<String, String> getOpenIdAndSessionKey(String code);
}
