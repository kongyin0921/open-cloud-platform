package com.ocp.auth.util;

import com.ocp.common.security.util.JwtUtils;
import com.ocp.common.util.JsonUtil;
import lombok.Getter;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.ocp.common.security.constants.IdTokenClaimNames.*;

/**
 * Oidc协议的IdToken
 * @author kong
 * @date 2021/08/09 22:22
 * blog: http://blog.kongyin.ltd
 */
@Getter
public class OidcIdTokenBuilder {
    private final Map<String, Object> claims;
    private final KeyProperties keyProperties;

    public OidcIdTokenBuilder(KeyProperties keyProperties) {
        this.claims = new LinkedHashMap<>();
        this.keyProperties = keyProperties;
    }

    public static OidcIdTokenBuilder builder(KeyProperties keyProperties){
        Assert.notNull(keyProperties, "keyProperties required");
        return new OidcIdTokenBuilder(keyProperties);
    }

    /**
     * Use this claim in the resulting {@link OidcIdTokenBuilder}
     *
     * @param name The claim name
     * @param value The claim value
     */
    public OidcIdTokenBuilder claim(String name, Object value) {
        this.claims.put(name, value);
        return this;
    }

    /**
     * Use this audience in the resulting {@link OidcIdTokenBuilder}
     *
     * @param audience The audience to use
     */
    public OidcIdTokenBuilder audience(String audience) {
        return claim(AUD, audience);
    }

    /**
     * Use this expiration in the resulting {@link OidcIdTokenBuilder}
     *
     * @param expiresAt The expiration to use
     */
    public OidcIdTokenBuilder expiresAt(long expiresAt) {
        return this.claim(EXP, expiresAt);
    }

    /**
     * Use this issued-at timestamp in the resulting {@link OidcIdTokenBuilder}
     *
     * @param issuedAt The issued-at timestamp to use
     */
    public OidcIdTokenBuilder issuedAt(long issuedAt) {
        return this.claim(IAT, issuedAt);
    }

    /**
     * Use this issuer in the resulting {@link OidcIdTokenBuilder}
     *
     * @param issuer The issuer to use
     */
    public OidcIdTokenBuilder issuer(String issuer) {
        return this.claim(ISS, issuer);
    }

    /**
     * Use this nonce in the resulting {@link OidcIdTokenBuilder}
     *
     * @param nonce The nonce to use
     */
    public OidcIdTokenBuilder nonce(String nonce) {
        return this.claim(NONCE, nonce);
    }

    /**
     * Use this subject in the resulting {@link OidcIdTokenBuilder}
     *
     * @param subject The subject to use
     */
    public OidcIdTokenBuilder subject(String subject) {
        return this.claim(SUB, subject);
    }

    /**
     * 赋值用户姓名
     */
    public OidcIdTokenBuilder name(String name) {
        return this.claim(NAME, name);
    }

    /**
     * 赋值用户登录名
     */
    public OidcIdTokenBuilder loginName(String loginName) {
        return this.claim(L_NAME, loginName);
    }

    /**
     * 赋值用户头像
     */
    public OidcIdTokenBuilder picture(String picture) {
        return this.claim(PIC, picture);
    }

    /**
     * Build the {@link OidcIdTokenBuilder}
     *
     * @return The constructed {@link OidcIdTokenBuilder}
     */
    public String build() {
        return JwtUtils.encodeStr(JsonUtil.toJSONString(claims), keyProperties);
    }
}
