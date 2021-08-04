package com.ocp.feign.ribbon.loadbalancer.filter;

import cn.hutool.core.util.StrUtil;
import com.ocp.common.constant.CommonConstant;
import com.ocp.common.constant.ConfigConstants;
import com.ocp.common.context.LbIsolationContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 负载均衡隔离规则过滤器
 * @author kong
 * @date 2021/08/04 22:52
 * blog: http://blog.kongyin.ltd
 */
@ConditionalOnClass(Filter.class)
public class LbIsolationFilter extends OncePerRequestFilter {
    @Value("${" + ConfigConstants.CONFIG_RIBBON_ISOLATION_ENABLED + ":false}")
    private boolean enableIsolation;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !enableIsolation;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {
            String version = request.getHeader(CommonConstant.O_C_P_VERSION);
            if(StrUtil.isNotEmpty(version)){
                LbIsolationContextHolder.setVersion(version);
            }

            filterChain.doFilter(request, response);
        } finally {
            LbIsolationContextHolder.clear();
        }
    }
}

