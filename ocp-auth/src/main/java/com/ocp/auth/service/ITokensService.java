package com.ocp.auth.service;

import com.ocp.auth.entity.dto.TokenQueryPageDto;
import com.ocp.auth.entity.vo.TokenVo;
import com.ocp.common.bean.PageInfo;

/**
 * @author kong
 * @date 2021/08/18 19:55
 * blog: http://blog.kongyin.ltd
 */
public interface ITokensService {
    /**
     * 查询token列表
     * @param pageDto 请求参数
     */
    PageInfo<TokenVo> getTokenPageByParam(TokenQueryPageDto pageDto);
}
