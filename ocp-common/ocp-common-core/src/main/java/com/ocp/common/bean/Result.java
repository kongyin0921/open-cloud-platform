package com.ocp.common.bean;

import com.ocp.common.code.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应结果集
 * @author kong
 * @date 2021/07/31 10:02
 * blog: http://blog.kongyin.ltd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    /**
     * 响应数据
     */
    private T datas;
    /**
     * 响应码
     */
    private Integer resp_code;
    /**
     * 响应信息
     */
    private String resp_msg;

    public static <T> Result<T> of(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg);
    }

    public static <T> Result<T> succeed(String msg) {
        return of(null, ResultCode.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model, String msg) {
        return of(model, ResultCode.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model) {
        return of(model, ResultCode.SUCCESS.getCode(), "");
    }

    public static <T> Result<T> failed(String msg) {
        return of(null, ResultCode.ERROR.getCode(), msg);
    }

    public static <T> Result<T> failed(T model, String msg) {
        return of(model, ResultCode.ERROR.getCode(), msg);
    }

}
