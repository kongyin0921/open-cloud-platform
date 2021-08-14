package com.ocp.common.code;

/**
 * 响应结果定义
 * @author kong
 * @date 2020/12/15 21:02
 */
public class CodeMsgs {

    /// =============================== 权限类型 ===============================
    /**
     * 认证失败（401）
     */
    public static CodeMsg AUTH_FAIL = CodeMsg.typeBuilder().type(CodeMsgType.OAUTH_FAIL).message("身份验证失败").build();
    /**
     * 无访问权限（403）
     */
    public static CodeMsg ACCESS_FAIL = CodeMsg.typeBuilder().type(CodeMsgType.ACCESS_FAIL).message("没有资源访问权限").build();

    /// =============================== 操作类型 ===============================
    /**
     * 成功（200）
     */
    public static CodeMsg REQUEST_SUCCESS = CodeMsg.typeBuilder().type(CodeMsgType.SUCCESS).message("请求成功").build();

    /// =============================== 服务全局错误 ===============================
    /**
     * 系统繁忙(-1)
     */
    public static CodeMsg SYS_BUSY = CodeMsg.builder().code("-1").message("系统繁忙").build();

    /**
     * 请求参数错误（400）
     */
    public static CodeMsg REQUEST_BAD = CodeMsg.typeBuilder().type(CodeMsgType.CLIENT_FAIL).message("请求错误").build();


    /// =============================== 业务异常 ===============================
    /**
     * 业务处理失败（210）
     */
    public static CodeMsg SERVICE_BASE_ERROR = CodeMsg.typeBuilder().type(CodeMsgType.BIZ_FAIL).message("业务处理失败").build();

    /// =============================== 系统错误 ===============================
    /**
     * 系统错误（500）
     */
    public static CodeMsg SYSTEM_BASE_ERROR = CodeMsg.typeBuilder().type(CodeMsgType.FAIL).message("系统错误").build();

}
