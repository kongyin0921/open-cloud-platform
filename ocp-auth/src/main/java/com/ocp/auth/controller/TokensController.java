package com.ocp.auth.controller;

import com.ocp.auth.entity.dto.TokenQueryPageDto;
import com.ocp.auth.entity.vo.TokenVo;
import com.ocp.auth.service.ITokensService;
import com.ocp.common.bean.PageInfo;
import com.ocp.common.bean.Result;
import com.ocp.common.code.CodeMsgs;
import com.ocp.common.constant.SecurityConstants;
import com.ocp.common.security.util.AuthUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * token管理接口
 * @author kong
 * @date 2021/08/18 19:46
 * blog: http://blog.kongyin.ltd
 */
@Api(tags = "Token管理")
@Slf4j
@RestController
@RequestMapping("/tokens")
public class TokensController {

    @Autowired
    private ITokensService tokensService;

    @Resource
    private ClientDetailsService clientDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @ApiOperation(value = "token列表")
    public ResponseEntity<PageInfo<TokenVo>> getTokenPageByParam(TokenQueryPageDto pageDto) {
        return ResponseEntity.ok(tokensService.getTokenPageByParam(pageDto));
    }

    @GetMapping("/key")
    @ApiOperation("获取jwt密钥")
    public ResponseEntity<Result<Object>> getKey(HttpServletRequest request) {
        try {
            //从header 请求中的clientId:clientSecret
            String[] clientArr = AuthUtils.extractClient(request);
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientArr[0]);
            if (clientDetails == null || !passwordEncoder.matches(clientArr[1], clientDetails.getClientSecret())) {
                throw new BadCredentialsException("应用ID或密码错误");
            }
        } catch (AuthenticationException ae) {
            return Result.obtain(CodeMsgs.AUTH_FAIL,ae.getMessage()).response();
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource(SecurityConstants.RSA_PUBLIC_KEY).getInputStream()))) {
            return Result.success(br.lines().collect(Collectors.joining("\n"))).response();
        } catch (IOException ioe) {
            log.error("key error", ioe);
            return Result.obtain(CodeMsgs.AUTH_FAIL,ioe.getMessage()).response();
        }
    }
}
