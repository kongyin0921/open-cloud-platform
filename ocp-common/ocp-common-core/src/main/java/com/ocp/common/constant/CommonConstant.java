package com.ocp.common.constant;

/**
 * 全局公共常量
 * @author kong
 * @date 2021/07/17 21:55
 * blog: http://blog.kongyin.ltd
 */
public interface CommonConstant {

    /**
     * 项目版本号(banner使用)
     */
    String PROJECT_VERSION = "1.0.0";

    /**
     * 注册中心元数据 版本号
     */
    String METADATA_VERSION = "version";

    /**
     * 授权请求类型
     */
    String BEARER_TYPE = "Bearer";

    /**
     * 标签 header key
     */
    String HEADER_LABEL = "x-label";

    /**
     * 标签 header 分隔符
     */
    String HEADER_LABEL_SPLIT = ",";

    /**
     * 标签或 名称
     */
    String LABEL_OR = "labelOr";

    /**
     * 标签且 名称
     */
    String LABEL_AND = "labelAnd";

    /**
     * 权重key
     */
    String WEIGHT_KEY = "weight";

    /**
     * 删除
     */
    String STATUS_DEL = "1";

    /**
     * 正常
     */
    String STATUS_NORMAL = "0";

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";

    /**
     * 目录
     */
    Integer CATALOG = -1;

    /**
     * 菜单
     */
    Integer MENU = 1;

    /**
     * 权限
     */
    Integer PERMISSION = 2;

    /**
     * 删除标记
     */
    String DEL_FLAG = "is_del";

    /**
     * 超级管理员用户名
     */
    String ADMIN_USER_NAME = "admin";

    /**
     * 默认密码
     */
    String DEF_USER_PASSWORD = "123456";

    /**
     * 锁键前缀
     */
    String LOCK_KEY_PREFIX = "LOCK_KEY";

    /**
     * 文件分隔符
     */
    String PATH_SPLIT = "/";
}
