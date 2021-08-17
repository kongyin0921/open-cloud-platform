package com.ocp.auth.provider.token;

import com.ocp.auth.entity.Client;
import com.ocp.auth.service.IClientService;
import com.ocp.auth.util.OidcIdTokenBuilder;
import com.ocp.common.constant.SecurityConstants;
import com.ocp.common.entity.SysUser;
import com.ocp.common.security.constants.IdTokenClaimNames;
import com.ocp.common.security.porperties.TokenStoreProperties;
import com.ocp.common.security.util.AuthUtils;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义的token
 * @author kong
 * @date 2021/08/17 19:56
 * blog: http://blog.kongyin.ltd
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    private final KeyProperties keyProperties;

    private final IClientService clientService;

    private final TokenStoreProperties tokenStoreProperties;

    public CustomTokenEnhancer(KeyProperties keyProperties, IClientService clientService, TokenStoreProperties tokenStoreProperties) {
        this.keyProperties = keyProperties;
        this.clientService = clientService;
        this.tokenStoreProperties = tokenStoreProperties;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Set<String> responseTypes = authentication.getOAuth2Request().getResponseTypes();
        Map<String, Object> additionalInfo = new HashMap<>(3);
        String accountType = AuthUtils.getAccountType(authentication.getUserAuthentication());
        additionalInfo.put(SecurityConstants.ACCOUNT_TYPE_PARAM_NAME, accountType);

        if (responseTypes.contains(SecurityConstants.ID_TOKEN)
                || "authJwt".equals(tokenStoreProperties.getType())) {
            Object principal = authentication.getPrincipal();
            //增加id参数
            if (principal instanceof SysUser) {
                SysUser user = (SysUser)principal;
                if (responseTypes.contains(SecurityConstants.ID_TOKEN)) {
                    //生成id_token
                    setIdToken(additionalInfo, authentication, keyProperties, clientService, user);
                }
                if ("authJwt".equals(tokenStoreProperties.getType())) {
                    additionalInfo.put("id", user.getId());
                }
            }
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    /**
     * 生成id_token
     * @param additionalInfo 存储token附加信息对象
     * @param authentication 授权对象
     * @param keyProperties 密钥
     * @param clientService 应用service
     */
    private void setIdToken(Map<String, Object> additionalInfo, OAuth2Authentication authentication
            , KeyProperties keyProperties, IClientService clientService, SysUser user) {
        String clientId = authentication.getOAuth2Request().getClientId();
        Client client = clientService.loadClientByClientId(clientId);
        if (client.getSupportIdToken()) {
            String nonce = authentication.getOAuth2Request().getRequestParameters().get(IdTokenClaimNames.NONCE);
            long now = System.currentTimeMillis();
            long expiresAt = System.currentTimeMillis() + client.getIdTokenValiditySeconds() * 1000;
            String idToken = OidcIdTokenBuilder.builder(keyProperties)
                    .issuer(SecurityConstants.ISS)
                    .issuedAt(now)
                    .expiresAt(expiresAt)
                    .subject(String.valueOf(user.getId()))
                    .name(user.getNickname())
                    .loginName(user.getUsername())
                    .picture(user.getHeadImgUrl())
                    .audience(clientId)
                    .nonce(nonce)
                    .build();

            additionalInfo.put(SecurityConstants.ID_TOKEN, idToken);
        }
    }
}
