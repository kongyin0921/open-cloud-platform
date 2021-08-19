package com.ocp.auth.entity.dto;

import com.ocp.common.bean.AbstractPageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kong
 * @date 2021/08/18 21:18
 * blog: http://blog.kongyin.ltd
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TokenQueryPageDto extends AbstractPageQuery {
    /**
     * 用户名
     */
    private String username;
    /**
     * 客户端 id
     */
    private String clientId;
}
