package com.ocp.common.filter;

import cn.hutool.core.util.StrUtil;
import com.ocp.common.constant.MessageHeaderConstants;
import com.ocp.common.context.ContextUserDetails;
import com.ocp.common.context.UserDetailsContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author kong
 * @date 2021/08/22 15:34
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
public class UserDetailsExtractFilter  extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //先从请求头获取
        String username = request.getHeader(MessageHeaderConstants.USER_HEADER);
        if (StrUtil.isEmpty(username)){
            // 获取令牌信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    // 用户令牌
                    log.info("令牌类型UserDetails");
                } else if (principal instanceof String) {
                    // 客户端令牌
                    log.info("令牌类型String");
                }

                ContextUserDetails contextUserDetails = new ContextUserDetails();
                //BeanUtils.copyProperties(loginUser, contextUserDetails);
                UserDetailsContextHolder.setContextUserDetails(contextUserDetails);

                filterChain.doFilter(request, response);
                log.debug("clean context user detail. ");
                UserDetailsContextHolder.cleanContextUserDetails();
            }
        }

    }
}
