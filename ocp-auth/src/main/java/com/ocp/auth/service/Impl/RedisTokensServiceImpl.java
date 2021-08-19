package com.ocp.auth.service.Impl;

import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ocp.auth.entity.dto.TokenQueryPageDto;
import com.ocp.auth.entity.vo.TokenVo;
import com.ocp.auth.service.ITokensService;
import com.ocp.common.bean.PageInfo;
import com.ocp.common.bean.SimplePage;
import com.ocp.common.constant.SecurityConstants;
import com.ocp.common.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * token管理服务(redis token)
 * @author kong
 * @date 2021/08/18 21:17
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Service
public class RedisTokensServiceImpl implements ITokensService {

    private final RedisRepository redisRepository;

    public RedisTokensServiceImpl(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public PageInfo<TokenVo> getTokenPageByParam(TokenQueryPageDto pageDto) {
        IPage<TokenVo> pageInfo = new SimplePage(pageDto);

        int start = PageUtil.getStart(pageDto.getPageIndex(), pageDto.getPageSize());
        int end = PageUtil.getEnd(pageDto.getPageIndex(), pageDto.getPageSize());
        //根据请求参数生成redis的key
        String redisKey = getRedisKey(pageDto);
        RedisSerializer<Object> serializer = RedisSerializer.java();
        List<TokenVo> result = new ArrayList<>(pageDto.getPageSize());
        List<Object> tokenObjs = redisRepository.getList(redisKey, start, end, serializer);
        if (tokenObjs != null) {
            for (Object tokenObj : tokenObjs) {
                DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken)tokenObj;
                //构造token对象
                TokenVo tokenVo = new TokenVo();
                tokenVo.setTokenValue(accessToken.getValue());
                tokenVo.setExpiration(accessToken.getExpiration());

                //获取用户信息
                OAuth2Authentication authentication = (OAuth2Authentication) redisRepository.get(
                        SecurityConstants.REDIS_TOKEN_AUTH.concat(accessToken.getValue()), serializer);

                if (authentication != null) {
                    OAuth2Request oAuth2Request = authentication.getOAuth2Request();
                    tokenVo.setUsername(authentication.getName());
                    tokenVo.setClientId(oAuth2Request.getClientId());
                    tokenVo.setGrantType(oAuth2Request.getGrantType());
                }

                String accountType = (String)accessToken.getAdditionalInformation().get(SecurityConstants.ACCOUNT_TYPE_PARAM_NAME);
                tokenVo.setAccountType(accountType);

                result.add(tokenVo);
            }
        }
        pageInfo.setRecords(result);
        return SimplePage.toPageResult(pageInfo);
    }

    private String getRedisKey(TokenQueryPageDto pageDto) {
        if (StrUtil.isNotEmpty(pageDto.getUsername())) {
            return  StrUtil.format("{}{}:{}",
                    SecurityConstants.REDIS_UNAME_TO_ACCESS,
                    pageDto.getClientId(),pageDto.getUsername());
        } else {
            return SecurityConstants.REDIS_CLIENT_ID_TO_ACCESS.concat(pageDto.getClientId());
        }
    }
}
