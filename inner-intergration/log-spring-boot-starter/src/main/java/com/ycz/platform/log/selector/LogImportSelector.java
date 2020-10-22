package com.ycz.platform.log.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName LogImportSelector
 * @Description 日志自动装配
 * @Auther kongyin
 * @Date 2020/10/22 9:33
 **/
public class LogImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                "com.open.capacity.log.aop.LogAnnotationAOP",
//				"com.open.capacity.log.config.SentryAutoConfig",
                "com.open.capacity.log.service.impl.LogServiceImpl",
                "com.open.capacity.log.config.LogAutoConfig"
        };
    }
}
