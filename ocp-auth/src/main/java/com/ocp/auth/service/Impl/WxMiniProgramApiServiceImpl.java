package com.ocp.auth.service.Impl;

import cn.hutool.json.JSONObject;
import com.ocp.auth.properties.WxMiniProgramProperties;
import com.ocp.auth.service.WxMiniProgramApiService;
import com.ocp.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kong
 * @date 2021/08/19 21:14
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(WxMiniProgramProperties.class)
public class WxMiniProgramApiServiceImpl implements WxMiniProgramApiService {

    @Autowired
    private WxMiniProgramProperties wxMiniProgramProperties;

    /**
     * 微信小程序获取openId和session_key
     *
     * @param code 小程序登录时获取的code
     * @return
     */
    @Override
    public Map<String, String> getOpenIdAndSessionKey(String code) {
        String appId = wxMiniProgramProperties.getAppId();
        String appSecret = wxMiniProgramProperties.getAppSecret();
        if (StringUtils.isEmpty(appId)) {
            throw new IllegalStateException("微信小程序appId未配置");
        }
        if (StringUtils.isEmpty(appSecret)) {
            throw new IllegalStateException("微信小程序appSecret未配置");
        }
        StringBuilder url = new StringBuilder();
        url.append("https://api.weixin.qq.com/sns/jscode2session?")
                .append("appid=").append(appId)
                .append("&secret=").append(appSecret)
                .append("&js_code=").append(code)
                .append("&grant_type=authorization_code");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url.toString()).get().build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject body = new JSONObject(response.body().string());

            if (!StringUtils.isEmpty(body.get("errcode"))) {
                String errMsg = body.getStr("errmsg");
                throw new ServiceException("微信小程序获取openId和session_key失败。" + errMsg);
            }
            Map<String, String> result = new HashMap<>(16);
            result.put("openId", body.getStr("openid"));
            result.put("sessionKey", body.getStr("session_key"));
            log.info("微信登录返回的结果：" + result);
            return result;
        } catch (IOException e) {
            log.error("微信小程序获取openId和session_key失败");
            throw new ServiceException("微信小程序获取openId和session_key失败。");
        }
    }
}
