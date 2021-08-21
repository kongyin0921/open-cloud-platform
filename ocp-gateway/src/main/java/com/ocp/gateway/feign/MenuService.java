package com.ocp.gateway.feign;

import com.ocp.common.constant.ServiceNameConstants;
import com.ocp.common.entity.SysMenu;
import com.ocp.gateway.feign.fallback.MenuServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author kong
 * @date 2021/08/20 22:12
 * blog: http://blog.kongyin.ltd
 */
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = MenuServiceFallbackFactory.class, decode404 = true)
public interface MenuService {
    /**
     * 角色菜单列表
     * @param roleCodes
     */
    @GetMapping(value = "/menus/{roleCodes}")
    List<SysMenu> findByRoleCodes(@PathVariable("roleCodes") String roleCodes);
}
