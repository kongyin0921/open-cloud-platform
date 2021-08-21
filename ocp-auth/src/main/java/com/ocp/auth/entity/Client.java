package com.ocp.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ocp.common.bean.AbstractEntity;
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
public class Client extends AbstractEntity<Client> {
    private static final long serialVersionUID = -8185413579135897885L;

    @TableField(value = "client_id")
    private String clientId;
    /**
     * 应用名称
     */
    @TableField(value = "client_name")
    private String clientName;
    @TableField("resource_ids")
    private String resourceIds = "";
    @TableField("client_secret")
    private String clientSecret;
    /**
     * 密钥 明文
     */
    @TableField("client_secret_str")
    private String clientSecretStr;
    @TableField("scope")
    private String scope = "all";
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;
    @TableField("authorities")
    private String authorities = "";
    @TableField(value = "access_token_validity")
    private Integer accessTokenValiditySeconds = 18000;
    @TableField(value = "refresh_token_validity")
    private Integer refreshTokenValiditySeconds = 28800;
    /**
     * 附加信息
     */
    @TableField("additional_information")
    private String additionalInformation = "{}";
    /**
     * 是否自动授权
     */
    @TableField("autoapprove")
    private Byte autoapprove = 1;
    /**
     * 是否支持id_token
     */
    @TableField("support_id_token")
    private Boolean supportIdToken = true;
    /**
     * id_token有效时间(s)
     */
    @TableField(value = "id_token_validity")
    private Integer idTokenValiditySeconds = 60;
}
