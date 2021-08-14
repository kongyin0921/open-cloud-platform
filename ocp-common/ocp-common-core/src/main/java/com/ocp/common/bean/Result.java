package com.ocp.common.bean;

import com.ocp.common.code.CodeMsg;
import com.ocp.common.code.CodeMsgType;
import com.ocp.common.code.CodeMsgs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * 响应结果集
 * @author kong
 * @date 2021/07/31 10:02
 * blog: http://blog.kongyin.ltd
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应代码
     */
    private String code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应详情
     */
    private String detail;
    /**
     * 结果数据
     */
    private T data;

    public Result(CodeMsg codeMsg, String detail, T data) {
        this.code = codeMsg.getCode();
        if (code == null || code.trim().equals("")) {
            throw new IllegalArgumentException("消息代码不能为空");
        }
        this.message = codeMsg.getMessage();
        this.detail = detail;
        this.data = data;
    }

    public Result() {
    }

    /**
     * 通过构建器创建对象
     *
     * @param builder 构建器
     */
    private Result(Builder<T> builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.detail = builder.detail;
        this.data = builder.data;

        if (builder.codeMsg != null) {
            CodeMsg codeMsg = builder.codeMsg;
            this.code = codeMsg.getCode();
            this.message = codeMsg.getMessage();
        }
        if (code == null || code.trim().equals("")) {
            throw new IllegalArgumentException("消息代码不能为空");
        }
    }

    /**
     * 成功结果
     *
     * @param detail 详细描述
     * @param data   数据内容
     * @param <T>    结果中数据对象类型
     * @return
     */
    public static <T> Result<T> success(String detail, T data) {
        return Result.<T>builder().codeMsg(CodeMsgs.REQUEST_SUCCESS).detail(detail).data(data).build();
    }

    /**
     * 成功结果
     *
     * @param detail 详细描述
     * @param <T>    结果中数据对象类型
     * @return
     */
    public static <T> Result<T> success(String detail) {
        return Result.<T>builder().codeMsg(CodeMsgs.REQUEST_SUCCESS).detail(detail).build();
    }

    /**
     * 成功结果
     *
     * @param <T> 结果中数据对象类型
     * @return 响应结果对象
     */
    public static <T> Result<T> success() {
        return Result.<T>builder().codeMsg(CodeMsgs.REQUEST_SUCCESS).build();
    }

    /**
     * 失败结果
     *
     * @param detail 详细描述
     * @param data   数据内容
     * @param <T>    结果中数据对象类型
     * @return 响应结果对象
     */
    public static <T> Result<T> error(String detail, T data) {
        return Result.<T>builder().codeMsg(CodeMsgs.SYSTEM_BASE_ERROR).detail(detail).data(data).build();
    }

    /**
     * 失败结果
     *
     * @param detail 详细描述
     * @param <T>    结果中数据对象类型
     * @return 响应结果对象
     */
    public static <T> Result<T> error(String detail) {
        return Result.<T>builder().codeMsg(CodeMsgs.SYSTEM_BASE_ERROR).detail(detail).build();
    }

    /**
     * 获取指定类型的响应结果
     *
     * @param codeMsg 结果代码对象
     * @return 响应结果对象
     */
    public static <T> Result<T> obtain(CodeMsg codeMsg) {
        return Result.<T>builder().codeMsg(codeMsg).build();
    }

    /**
     * 获取指定类型的响应结果
     *
     * @param codeMsg 结果代码对象
     * @param detail  结果信息
     * @param <T>     结果数据类型
     * @return 响应结果对象
     */
    public static <T> Result<T> obtain(CodeMsg codeMsg, String detail) {
        return Result.<T>builder().codeMsg(codeMsg).detail(detail).build();
    }

    /**
     * 获取指定类型的响应结果
     *
     * @param codeMsg 结果代码对象
     * @param detail  结果信息
     * @param data    数据对象
     * @param <T>     数据类型
     * @return 响应结果对象
     */
    public static <T> Result<T> obtain(CodeMsg codeMsg, String detail, T data) {
        return Result.<T>builder().codeMsg(codeMsg).detail(detail).data(data).build();
    }


    /**
     * 创建构造器
     *
     * @param <T> 构造器结果数据对象类型
     * @return 构造器
     */
    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    /**
     * 通过serviceResult创建响应结果对象
     *
     * @param serviceResult 业务处理结果对象
     * @param <T>           结果数据对象类型
     * @return 响应结果对象
     */
    /*public static <T> ResponseResult<T> build(ServiceResult<T> serviceResult) {
        return ResponseResult.<T>builder().code(serviceResult.getCode()).message(serviceResult.getMessage())
                .detail(serviceResult.getDetail()).data(serviceResult.getData()).build();
    }*/

    /**
     * 构建器类
     *
     * @param <T> 构建Result对象中数据的类型
     */
    public static class Builder<T> {

        private String code;

        private String message;

        private String detail;

        private T data;

        private CodeMsg codeMsg;

        public Builder<T> code(String code) {
            this.code = code;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> codeMsg(CodeMsg codeMsg) {
            this.codeMsg = codeMsg;
            return this;
        }

        public Result<T> build() {
            return new Result<T>(this);
        }
    }

    /**
     * 返回指定dto数据的响应结果
     *
     * @param dto 结果数据
     * @param <T> 数据类型
     * @return 响应结果对象
     */
    public static <T> ResponseEntity<T> response(T dto) {
        if (dto == null) {
            // 返回空结果
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * 返回http响应结果
     *
     * @return 响应结果对象
     */
    public ResponseEntity<Result<T>> response() {
        String code = this.getCode();
        if (code == null || "".equals(code.trim())) {
            throw new IllegalArgumentException("消息代码不能为空");
        }
        ResponseEntity<Result<T>> responseEntity;
        if (code.startsWith(CodeMsgType.SUCCESS.getCode())) {
            responseEntity = ResponseEntity.ok(this);
        } else if (code.startsWith(CodeMsgType.FAIL.getCode())) {
            // 系统错误
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(this);
        } else if (code.startsWith(CodeMsgType.OAUTH_FAIL.getCode())) {
            // 身份认证错误
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(this);
        } else if (code.startsWith(CodeMsgType.ACCESS_FAIL.getCode())) {
            // 无资源访问权限
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(this);
        } else if (code.startsWith(CodeMsgType.CLIENT_FAIL.getCode())) {
            // 客户端错误
            responseEntity = ResponseEntity.badRequest().body(this);
        } else if (code.startsWith(CodeMsgType.BIZ_FAIL.getCode())) {
            // 业务处理结果
            responseEntity = ResponseEntity.status(210).body(this);
        } else {
            throw new IllegalArgumentException("未定义返回状态码[" + code + "]");
        }
        return responseEntity;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
