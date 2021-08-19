package com.ocp.auth.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ocp.auth.entity.Client;
import com.ocp.auth.entity.dto.ClientQueryPageDto;
import com.ocp.auth.mapper.ClientMapper;
import com.ocp.auth.service.IClientService;
import com.ocp.common.bean.PageInfo;
import com.ocp.common.bean.SimplePage;
import com.ocp.common.bean.SuperServiceImpl;
import com.ocp.common.constant.SecurityConstants;
import com.ocp.common.lock.DistributedLock;
import com.ocp.common.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author kong
 * @date 2021/08/12 21:50
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class ClientServiceImpl extends SuperServiceImpl<ClientMapper, Client> implements IClientService {

    private final static String LOCK_KEY_CLIENTID = "clientId:";

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DistributedLock lock;

    @Override
    public PageInfo<Client> listClient(ClientQueryPageDto pageDto) {
        IPage<Client> page = new SimplePage(pageDto);
        LambdaQueryWrapper<Client> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StrUtil.isNotBlank(pageDto.getClientId()), Client::getClientId, pageDto.getClientId())
                .eq(StrUtil.isNotBlank(pageDto.getResourceIds()), Client::getResourceIds, pageDto.getResourceIds())
                .eq(StrUtil.isNotBlank(pageDto.getClientSecret()), Client::getClientSecret, pageDto.getClientSecret())
                .eq(StrUtil.isNotBlank(pageDto.getScope()), Client::getScope, pageDto.getScope())
                .eq(StrUtil.isNotBlank(pageDto.getAuthorities()), Client::getAuthorities, pageDto.getAuthorities())
                .eq(StrUtil.isNotBlank(pageDto.getAuthorizedGrantTypes()), Client::getAuthorizedGrantTypes, pageDto.getAuthorizedGrantTypes())
                .eq(StrUtil.isNotBlank(pageDto.getWebServerRedirectUri()), Client::getWebServerRedirectUri, pageDto.getWebServerRedirectUri())
                .eq(StrUtil.isNotBlank(pageDto.getAccessTokenValidity()), Client::getAccessTokenValiditySeconds, pageDto.getAccessTokenValidity())
                .eq(StrUtil.isNotBlank(pageDto.getRefreshTokenValidity()), Client::getRefreshTokenValiditySeconds, pageDto.getRefreshTokenValidity())
                .eq(StrUtil.isNotBlank(pageDto.getAdditionalInformation()), Client::getAdditionalInformation, pageDto.getAdditionalInformation())
                .eq(StrUtil.isNotBlank(pageDto.getAutoApprove()), Client::getAutoApprove, pageDto.getAutoApprove());
        page = baseMapper.selectPage(page,lambdaQueryWrapper);
        return SimplePage.toPageResult(page);
    }

    @Override
    public void saveClient(Client client) throws Exception {
        client.setClientSecret(passwordEncoder.encode(client.getClientSecretStr()));
        String clientId = client.getClientId();
        super.saveOrUpdateIdempotency(client, lock
                , LOCK_KEY_CLIENTID.concat(clientId)
                , new QueryWrapper<Client>().lambda().eq(Client::getClientId, clientId)
                , clientId.concat("已存在"));
    }

    @Override
    public Client loadClientByClientId(String clientId) {
        QueryWrapper<Client> wrapper = Wrappers.query();
        wrapper.lambda().eq(Client::getClientId, clientId);
        return this.getOne(wrapper);
    }

    @Override
    public void delClient(Long id) {
        String clientId = baseMapper.selectById(id).getClientId();
        baseMapper.deleteById(id);
        redisRepository.del(clientRedisKey(clientId));
    }

    private String clientRedisKey(String clientId) {
        return SecurityConstants.CACHE_CLIENT_KEY.concat(clientId);
    }
}



































