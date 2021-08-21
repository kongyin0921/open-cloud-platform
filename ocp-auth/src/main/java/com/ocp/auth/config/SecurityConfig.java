package com.ocp.auth.config;

import com.ocp.auth.filter.LoginProcessSetTenantFilter;
import com.ocp.auth.filter.TenantUserPwdAuthenticationFilter;
import com.ocp.auth.provider.*;
import com.ocp.common.constant.EndpointConstant;
import com.ocp.common.constant.SecurityConstants;
import com.ocp.common.properties.TenantProperties;
import com.ocp.common.security.token.CustomWebAuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * spring security配置
 * 在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
 * @author kong
 * @date 2021/08/12 22:39
 * blog: http://blog.kongyin.ltd
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired(required = false)
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private TenantProperties tenantProperties;

    @Autowired
    private PasswordAuthenticationProvider passwordAuthenticationProvider;

    @Autowired
    private MobileAuthenticationProvider mobileAuthenticationProvider;

    @Autowired
    private OpenIdAuthenticationProvider openIdAuthenticationProvider;

    @Autowired
    private TenantAuthenticationProvider tenantAuthenticationProvider;

    @Autowired
    private WxMiniProgramProvider wxMiniProgramProvider;



    /**
     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     * @return 认证管理对象
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 身份验证详细信息来源
     * @return
     */
    @Bean
    public AuthenticationDetailsSource<HttpServletRequest, CustomWebAuthenticationDetails> authenticationDetailsSource(){
        return (context) -> {
            String remoteAddress = context.getRemoteAddr();
            HttpSession session = context.getSession(false);
            String sessionId = session != null ? session.getId() : null;
            String accountType = context.getParameter(SecurityConstants.ACCOUNT_TYPE_PARAM_NAME);
            return new CustomWebAuthenticationDetails(remoteAddress, sessionId, accountType);
        };
    }

    @Bean
    public TenantUserPwdAuthenticationFilter tenantAuthenticationFilter(AuthenticationManager authenticationManager) {
        TenantUserPwdAuthenticationFilter filter = new TenantUserPwdAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setFilterProcessesUrl(EndpointConstant.OAUTH_LOGIN_PRO_URL);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationDetailsSource(authenticationDetailsSource());
        return filter;
    }

    /**
     * 全局用户信息
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(passwordAuthenticationProvider);
        auth.authenticationProvider(mobileAuthenticationProvider);
        auth.authenticationProvider(openIdAuthenticationProvider);
        auth.authenticationProvider(tenantAuthenticationProvider);
        auth.authenticationProvider(wxMiniProgramProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resource/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(EndpointConstant.OAUTH_ALL,EndpointConstant.LOGIN).permitAll()//除授权相关path拦截所有请求
                .antMatchers("/**").authenticated()//要使用antMatchers("/**")不能使用 anyRequest 会屏蔽静态资源
                .and().logout().logoutUrl(EndpointConstant.LOGOUT_URL)
                .logoutSuccessHandler(logoutSuccessHandler)
                .addLogoutHandler(logoutHandler).clearAuthentication(true)
                .and().addFilterBefore(new LoginProcessSetTenantFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable().headers().frameOptions().disable().cacheControl();// 解决不允许显示在iframe的问题

        if (tenantProperties.getEnable()) {
            //解决不同租户单点登录时角色没变化
            http.formLogin().loginPage(EndpointConstant.LOGIN).and()
                    .addFilterAt(tenantAuthenticationFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
        } else {
            http.formLogin().loginPage(EndpointConstant.LOGIN)
                    .loginProcessingUrl(EndpointConstant.OAUTH_LOGIN_PRO_URL)
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                    .authenticationDetailsSource(authenticationDetailsSource());
        }


        // 基于密码 等模式可以无session,不支持授权码模式
        if (authenticationEntryPoint != null) {
            http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        } else {
            // 授权码模式单独处理，需要session的支持，此模式可以支持所有oauth2的认证
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        }

    }
}
