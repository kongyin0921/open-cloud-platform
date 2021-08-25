package com.ocp.gateway.auth;

import com.ocp.common.entity.SysMenu;
import com.ocp.common.security.service.impl.AbstractPermissionServiceImpl;
import com.ocp.gateway.feign.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * url权限认证
 * @author kong
 * @date 2021/08/25 20:15
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Component
public class PermissionAuthManager extends AbstractPermissionServiceImpl implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Resource
    private MenuService menuService;

    @Override
    public List<SysMenu> findMenuByRoleCodes(String roleCodes) {
        return menuService.findByRoleCodes(roleCodes);
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        return mono.map(auth ->{
            ServerHttpRequest request = authorizationContext.getExchange().getRequest();
            boolean isPermission = super.hasPermission(auth, request.getMethodValue(), request.getURI().getPath());
            return new AuthorizationDecision(isPermission);
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }
}
