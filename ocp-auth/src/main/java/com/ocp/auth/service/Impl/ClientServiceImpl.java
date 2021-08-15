package com.ocp.auth.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocp.auth.entity.Client;
import com.ocp.auth.mapper.ClientMapper;
import com.ocp.auth.service.IClientService;
import com.ocp.common.bean.PageResult;
import com.ocp.common.bean.SuperServiceImpl;
import com.ocp.common.lock.DistributedLock;
import com.ocp.common.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public PageResult<Client> listClient(Map<String, Object> params, boolean isPage) {
        Page<Client> page;
        if (isPage) {
            page = new Page<>(MapUtils.getInteger(params,"page"),MapUtils.getInteger(params,"limit"));
        } else {
            page = new Page<>(1,-1);
        }
        List<Client> list = baseMapper.findList(page,params);
        page.setRecords(list);
        return PageResult.<Client>builder().data(list).code(0).count(page.getTotal()).build();
    }

    @Override
    public void saveClient(Client client) throws Exception {
        client.setClientSecret(passwordEncoder.encode(client.getClientSecretStr()));
        String clientId = client.getClientId();
        super.saveOrUpdateIdempotency(client, lock
                , LOCK_KEY_CLIENTID.concat(clientId)
                , new QueryWrapper<Client>().eq(OAuth2Utils.CLIENT_ID, clientId)
                , clientId + "已存在");
    }
}



































