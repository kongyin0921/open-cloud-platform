package com.ocp.auth.provider.error;

import com.ocp.common.bean.Result;
import com.ocp.common.code.CodeMsgs;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * Rest Web响应异常转换器
 * @author kong
 * @date 2021/08/15 10:29
 * blog: http://blog.kongyin.ltd
 */
public class RestWebResponseExceptionTranslator implements WebResponseExceptionTranslator<Result<Object>> {
    private final DefaultWebResponseExceptionTranslator defaultWebResponseExceptionTranslator = new DefaultWebResponseExceptionTranslator();

    @Override
    public ResponseEntity<Result<Object>> translate(Exception e) throws Exception {

        ResponseEntity<OAuth2Exception> translate = defaultWebResponseExceptionTranslator.translate(e);
        OAuth2Exception oAuth2Exception = translate.getBody();
        assert oAuth2Exception != null;
        oAuth2Exception.getHttpErrorCode();
        oAuth2Exception.getOAuth2ErrorCode();
        String message = e.getMessage();
        /**
         * <code>oAuth2Exception.getMessage();</code>
         */
        return Result.builder().codeMsg(CodeMsgs.AUTH_FAIL).detail(message).build().response();

    }
}
