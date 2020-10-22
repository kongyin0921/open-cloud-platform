package com.ycz.platform.log.annotation;

import java.lang.annotation.*;

/**
 * @ClassName LogAnnotation
 * @Description 日志
 * @Auther kongyin
 * @Date 2020/10/21 14:43
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /**
     * 模块
     */
    String module();

    /**
     * 记录执行
     */
    boolean recordRequestParam() default false;
}
