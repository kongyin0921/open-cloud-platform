package com.ocp.auth.provider.code;

import com.ocp.common.constant.SecurityConstants;
import com.ocp.common.redis.template.RedisRepository;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *
 * JdbcAuthorizationCodeServices替换
 * @author kong
 * @date 2021/08/16 21:32
 * blog: http://blog.kongyin.ltd
 */
@Service
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
    private final RedisRepository redisRepository;
    private final RedisSerializer<Object> valueSerializer;

    public RedisAuthorizationCodeServices(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
        this.valueSerializer = RedisSerializer.java();
    }

    @Override
    protected void store(String code, OAuth2Authentication oAuth2Authentication) {
        redisRepository.setExpire(redisKey(code), oAuth2Authentication,
                SecurityConstants.OAUTH_CODE_EXPIRE, TimeUnit.MINUTES, valueSerializer);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        String codeKey = redisKey(code);
        OAuth2Authentication token = (OAuth2Authentication) redisRepository.get(codeKey, valueSerializer);
        redisRepository.del(codeKey);
        return token;
    }

    /**
     * redis中 code key的前缀
     *
     * @param code
     * @return
     */
    private String redisKey(String code) {
        return SecurityConstants.OAUTH_CODE.concat(code);
    }
}
