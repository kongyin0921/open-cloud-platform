package com.ycz.platform.uaa.server.service;

import com.open.capacity.common.constant.UaaConstant;
import com.open.capacity.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;
import java.util.List;

/**
 * @ClassName RedisClientDetailsService
 * @Description 类说明
 *  * 将oauth_client_details表数据缓存到redis，这里做个缓存优化
 *  * layui模块中有对oauth_client_details的crud， 注意同步redis的数据
 *  * 注意对oauth_client_details清楚redis db部分数据的清空
 * @Auther kongyin
 * @Date 2020/10/29 9:17
 **/
@Slf4j
public class RedisClientDetailsService extends JdbcClientDetailsService {

    private static final String SELECT_CLIENT_DETAILS_SQL = "select client_id, client_secret, resource_ids, scope, authorized_grant_types, " +
            "web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove ,if_limit, limit_count ,id " +
            "from oauth_client_details where client_id = ? and status = 1  ";
    // 扩展 默认的 ClientDetailsService, 增加逻辑删除判断( status = 1)
    private static final String SELECT_FIND_STATEMENT = "select client_id, client_secret,resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove ,if_limit, limit_count ,id  from oauth_client_details where status = 1 order by client_id " ;

    private RedisTemplate<String,Object> restTemplate;

    public RedisTemplate<String, Object> getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RedisTemplate<String, Object> restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final JdbcTemplate jdbcTemplate;

    public RedisClientDetailsService(DataSource dataSource) {
        super(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails = null;
        //先从redis获取
        String value = (String) restTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).get(clientId);
        if(StringUtil.isBlank(value)){

        }else {

        }
        return super.loadClientByClientId(clientId);
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        super.addClientDetails(clientDetails);
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        super.updateClientSecret(clientId, secret);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        super.removeClientDetails(clientId);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return super.listClientDetails();
    }
}
