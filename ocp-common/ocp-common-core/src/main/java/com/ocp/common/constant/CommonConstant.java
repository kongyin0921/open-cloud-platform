package com.ocp.common.constant;

/**
 * 全局公共常量
 * @author kong
 * @date 2021/07/17 21:55
 * blog: http://blog.kongyin.ltd
 */
public interface CommonConstant {

    /**
     * 公共日期格式
     */
    String MONTH_FORMAT = "yyyy-MM";
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String SIMPLE_MONTH_FORMAT = "yyyyMM";
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    String SIMPLE_DATETIME_FORMAT = "yyyyMMddHHmmss";
    String TIME_ZONE_GMT8 = "GMT+8";

    /**
     * 锁
     */
    String LOCK_KEY_PREFIX = "LOCK_KEY";

    /**
     * 文件分隔符
     */
    String PATH_SPLIT = "/";
}
