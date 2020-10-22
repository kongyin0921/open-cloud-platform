package com.ycz.platform.log.annotation;


import com.ycz.platform.log.selector.LogImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ClassName EnableLogging
 * @Description 开启日志
 * @Auther kongyin
 * @Date 2020/10/21 14:42
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogImportSelector.class)
public @interface EnableLogging {
}
