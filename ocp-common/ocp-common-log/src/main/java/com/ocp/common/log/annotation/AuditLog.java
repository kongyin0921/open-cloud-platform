package com.ocp.common.log.annotation;

import java.lang.annotation.*;

/**
 * @author kong
 * @date 2021/08/08 12:08
 * blog: http://blog.kongyin.ltd
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    /**
     * 操作信息
     */
    String operation();
}
