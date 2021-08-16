package com.ocp.auth.provider.client;

import cn.hutool.core.collection.CollectionUtil;
import com.ocp.common.constant.SecurityConstants;
import com.ocp.common.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 *
 * 将oauth_client_details表数据缓存到redis，这里做个缓存优化
 * 有对oauth_client_details的crud， 都将同步redis的数据
 * 注意对oauth_client_details清除redis db部分数据也同步清空
 * @author kong
 * @date 2021/08/16 20:58
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@Service
public class RedisClientDetailsService extends JdbcClientDetailsService {
    private final RedisRepository redisRepository;

    public RedisClientDetailsService(DataSource dataSource, RedisRepository redisRepository) {
        super(dataSource);
        this.redisRepository = redisRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        //先从 redis 获取
        ClientDetails clientDetails = (ClientDetails)redisRepository.get(clientRedisKey(clientId));
        if (clientDetails == null) {
            clientDetails = this.cacheAndGetClient(clientId);
        }
        return clientDetails;
    }

    /**
     * 缓存client并返回client
     * @param clientId 客户端
     * @return
     */
    private ClientDetails cacheAndGetClient(String clientId) {
        ClientDetails clientDetails = null;
        try {
            //读取 数据库
            clientDetails = super.loadClientByClientId(clientId);
            if (clientDetails != null) {
                // 写入 redis 缓存
                redisRepository.set(clientRedisKey(clientId), clientDetails);
                log.info("缓存clientId:{},{}", clientId, clientDetails);
            }
        } catch (NoSuchClientException e) {
            log.error("clientId:{},{}", clientId, clientId);
        } catch (InvalidClientException e) {
            log.error("cacheAndGetClient-invalidClient:{}", clientId, e);
        }
        return clientDetails;
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        super.addClientDetails(clientDetails);
        this.cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
        this.cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        super.updateClientSecret(clientId, secret);
        cacheAndGetClient(clientId);
        this.removeRedisCache(clientId);
    }

    /**
     * 删除 redis 缓存
     * @param clientId 客户id
     */
    private void removeRedisCache(String clientId) {
        redisRepository.del(clientRedisKey(clientId));
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        super.removeClientDetails(clientId);
    }

    private String clientRedisKey(String clientId) {
        return SecurityConstants.CACHE_CLIENT_KEY.concat(clientId);
    }

    /**
     * 将oauth_client_details全表 存储 redis
     */
    public void loadAllClientToCache() {
        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtil.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }
        list.forEach(client -> redisRepository.set(clientRedisKey(client.getClientId()),client));
    }
}
