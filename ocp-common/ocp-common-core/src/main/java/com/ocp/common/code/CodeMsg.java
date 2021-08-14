package com.ocp.common.code;

/**
 * 结果代码对象定义
 *
 * @author kong
 * @date 2020/12/15 20:49
 */
public class CodeMsg {

    /**
     * 结果代码
     */
    private String code;

    /**
     * 结果信息
     */
    private String message;


    public CodeMsg() {
    }

    public CodeMsg(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param builder 构造器
     */
    public CodeMsg(CodeMsgBuilder builder) {
        this.code = builder.code;
        this.message = builder.message;
    }

    /**
     * 使用类型构建器构建消息代码
     *
     * @param builder 类型构建器
     */
    public CodeMsg(CodeMsgTypeBuilder builder) {
        //this.code = builder.typeCode.getCode() + builder.suffixCode;
        this.code = builder.typeCode.getCode();
        this.message = builder.message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 创建构造器
     *
     * @return 代码构造器
     */
    public static CodeMsgBuilder builder() {
        return new CodeMsgBuilder();
    }

    /**
     * 创建类型代码构造器
     *
     * @return 类型代码构造器
     */
    public static CodeMsgTypeBuilder typeBuilder() {
        return new CodeMsgTypeBuilder();
    }

    /**
     * CodeMsg 类型建造器
     */
    public static class CodeMsgTypeBuilder {
        /**
         * 类型代码
         */
        private CodeMsgType typeCode;
        /**
         * 后缀代码
         */
        //private String suffixCode;
        /**
         * 代码描述
         */
        private String message;

        public CodeMsgTypeBuilder type(CodeMsgType type) {
            this.typeCode = type;
            return this;
        }

        /*public CodeMsgTypeBuilder suffixCode(String suffixCode) {
            this.suffixCode = suffixCode;
            return this;
        }*/

        public CodeMsgTypeBuilder message(String message) {
            this.message = message;
            return this;
        }

        public CodeMsg build() {
            return new CodeMsg(this);
        }
    }

    /**
     * CodeMsg 建造器
     */
    public static class CodeMsgBuilder {
        private String code;
        private String message;

        public CodeMsgBuilder code(String code) {
            this.code = code;
            return this;
        }

        public CodeMsgBuilder message(String message) {
            this.message = message;
            return this;
        }

        public CodeMsg build() {
            return new CodeMsg(this);
        }

    }


}