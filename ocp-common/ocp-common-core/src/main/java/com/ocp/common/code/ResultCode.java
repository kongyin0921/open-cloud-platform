package com.ocp.common.code;

/**
 * 响应code枚举
 * @author kong
 * @date 2021/07/31 10:06
 * blog: http://blog.kongyin.ltd
 */
public enum ResultCode {

    SUCCESS(0),
    ERROR(1);

    private Integer code;

    ResultCode(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
