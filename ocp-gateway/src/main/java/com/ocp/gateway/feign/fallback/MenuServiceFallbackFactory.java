package com.ocp.gateway.feign.fallback;

import cn.hutool.core.collection.CollectionUtil;
import com.ocp.gateway.feign.MenuService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author kong
 * @date 2021/08/20 22:13
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Component
public class MenuServiceFallbackFactory implements FallbackFactory<MenuService> {
    @Override
    public MenuService create(Throwable throwable) {
        return roleCodes -> {
            log.error("调用findByRoleCodes异常：{}", roleCodes, throwable);
            return CollectionUtil.newArrayList();
        };
    }
}
