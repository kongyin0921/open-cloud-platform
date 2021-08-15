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


    /**
     * 获取验证码
     * @param deviceId 前端唯一标识/手机号
     */
    String getCode(String deviceId);

    /**
     * 删除验证码
     * @param deviceId 前端唯一标识/手机号
     */
    void remove(String deviceId);

    /**
     * 验证验证码
     * @param deviceId 设备编号
     * @param validCode 有效代码
     */
    void validate(String deviceId, String validCode);
}
