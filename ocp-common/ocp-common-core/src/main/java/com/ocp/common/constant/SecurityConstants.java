package com.ocp.common.constant;

/**
 *
 * Security 权限常量
 * @author kong
 * @date 2021/07/31 14:00
 * blog: http://blog.kongyin.ltd
 */
public interface SecurityConstants {
    /**
     * 用户信息分隔符
     */
    String USER_SPLIT = ":";

    /**
     * 基础角色
     */
    String BASE_ROLE = "ROLE_USER";

    /**
     * 默认生成图形验证码过期时间
     */
    int DEFAULT_IMAGE_EXPIRE = 60;

    /**
     * 边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.
     */
    String DEFAULT_COLOR_FONT = "blue";

    /**
     * 图片边框
     */
    String DEFAULT_IMAGE_BORDER = "no";

    /**
     * 默认图片间隔
     */
    String DEFAULT_CHAR_SPACE = "5";

    /**
     * 默认保存code的前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY";

    /**
     * 验证码文字大小
     */
    String DEFAULT_IMAGE_FONT_SIZE = "30";

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    String CACHE_CLIENT_KEY = "oauth_client_details";

    /**
     * 默认token过期时间(1小时)
     */
    Integer ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;

    /**
     * redis中授权token对应的key
     */
    String REDIS_TOKEN_AUTH = "auth:";

    /**
     * redis中应用对应的token集合的key
     */
    String REDIS_CLIENT_ID_TO_ACCESS = "client_id_to_access:";

    /**
     * redis中用户名对应的token集合的key
     */
    String REDIS_UNAME_TO_ACCESS = "uname_to_access:";

    /**
     * rsa公钥
     */
    String RSA_PUBLIC_KEY = "pubkey.txt";

    /**
     * 获取id_token的response_type
     */
    String ID_TOKEN = "id_token";

    /**
     * 令牌颁发者
     */
    String ISS = "http://xxx.cn";

    /**
     * 账号类型参数名
     */
    String ACCOUNT_TYPE_PARAM_NAME = "account_type";
}
