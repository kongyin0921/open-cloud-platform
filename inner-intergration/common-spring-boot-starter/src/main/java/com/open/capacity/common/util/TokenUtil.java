package com.open.capacity.common.util;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName TokenUtil
 * @Description TODO
 * @Auther kongyin
 * @Date 2020/10/22 16:20
 **/
public class TokenUtil {

    public static String getToken() {
        String token = "";
        /*try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            String header = request.getHeader(UaaConstant.AUTHORIZATION);

            token = StringUtil.isBlank(StringUtil.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ")) ? request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) : StringUtil.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ");

            token = StringUtil.isBlank(request.getHeader(UaaConstant.TOKEN_HEADER)) ? token : request.getHeader(UaaConstant.TOKEN_HEADER);
        } catch (IllegalStateException e) {
        }*/
        return token;

    }
}
