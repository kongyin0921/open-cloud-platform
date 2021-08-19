package com.ocp.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ocp.common.bean.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kong
 * @date 2021/08/11 22:13
 * blog: http://blog.kongyin.ltd
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_details")
public class Client extends SuperEntity {
    private static final long serialVersionUID = -8185413579135897885L;
    private String clientId;
    /**
     * 应用名称
     */
    private String clientName;
    private String resourceIds = "";
    private String clientSecret;
    private String clientSecretStr;
    private String scope = "all";
    private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
    private String webServerRedirectUri;
    private String authorities = "";
    @TableField(value = "access_token_validity")
    private Integer accessTokenValiditySeconds = 18000;
    @TableField(value = "refresh_token_validity")
    private Integer refreshTokenValiditySeconds = 28800;
    private String additionalInformation = "{}";
    private String autoApprove = "true";
    /**
     * 是否支持id_token
     */
    private Boolean supportIdToken = true;
    /**
     * id_token有效时间(s)
     */
    @TableField(value = "id_token_validity")
    private Integer idTokenValiditySeconds = 60;
}
