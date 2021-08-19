package com.ocp.auth.entity.dto;

import com.ocp.common.bean.AbstractPageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端 查询 分页 dto
 * @author kong
 * @date 2021/08/18 20:17
 * blog: http://blog.kongyin.ltd
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientQueryPageDto extends AbstractPageQuery {
    /**
     * 客户端 id
     */
    private String clientId;
    /**
     * 资源 ID
     */
    private String resourceIds;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * 授权 范围
     */
    private String scope;
    /**
     * 授权类型
     */
    private String authorizedGrantTypes;
    /**
     * Web 服务器重定向 Uri
     */
    private String webServerRedirectUri;
    /**
     *
     */
    private String authorities;
    /**
     * 访问令牌有效性
     */
    private String accessTokenValidity;
    /**
     * 刷新令牌有效性
     */
    private String refreshTokenValidity;
    /**
     * 附加信息
     */
    private String additionalInformation;
    /**
     * 是否自动授权
     */
    private String autoApprove;

}
