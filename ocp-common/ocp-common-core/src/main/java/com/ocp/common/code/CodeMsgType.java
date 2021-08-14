package com.ocp.common.code;

/**
 * 消息代码类型
 *
 * @author kong
 * @date 2020/12/15 20:52
 */
public enum  CodeMsgType {

    SUCCESS("200", "请求成功"),

    FAIL("500", "系统错误"),

    BIZ_FAIL("210", "业务错误"),

    CLIENT_FAIL("400", "客户端错误"),

    OAUTH_FAIL("401", "身份认证失败"),

    ACCESS_FAIL("403", "访问权限不足");

    /**
     * 代码
     */
    private String code;
    /**
     * 描述
     */
    private String descr;

    /**
     * 构造方法
     *
     * @param code
     * @param descr
     */
    CodeMsgType(String code, String descr) {
        this.code = code;
        this.descr = descr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
