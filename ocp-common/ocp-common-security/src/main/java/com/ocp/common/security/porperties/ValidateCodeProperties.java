package com.ocp.common.security.porperties;

import lombok.Data;

/**
 * 验证代码属性
 * @author kong
 * @date 2021/07/29 22:36
 * blog: http://blog.kongyin.ltd
 */
@Data
public class ValidateCodeProperties {
    /**
     * 设置认证时不需要验证码的clientId
     */
    private String[] ignoreClientCode = {};
}
