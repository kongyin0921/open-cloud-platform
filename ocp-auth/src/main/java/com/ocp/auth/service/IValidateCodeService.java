package com.ocp.auth.service;


/**
 * @author kong
 * @date 2021/08/13 22:35
 * blog: http://blog.kongyin.ltd
 */
public interface IValidateCodeService {
    /**
     * 保存图形验证码
     * @param deviceId 前端唯一标识
     * @param imageCode 验证码
     */
    void saveImageCode(String deviceId, String imageCode);

    /**
     * 发送短信代码
     * @param mobile 手机
     */
    void sendSmsCode(String mobile);
}
