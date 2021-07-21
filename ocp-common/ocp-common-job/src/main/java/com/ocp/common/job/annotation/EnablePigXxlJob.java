package com.ocp.common.job.annotation;

import com.ocp.common.job.XxlJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author kong
 * @date 2021/07/21 21:29
 * blog: http://blog.kongyin.ltd
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({XxlJobAutoConfiguration.class})
public @interface EnablePigXxlJob {
}
