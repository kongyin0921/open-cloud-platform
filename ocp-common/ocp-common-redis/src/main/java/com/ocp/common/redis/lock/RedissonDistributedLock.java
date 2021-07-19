package com.ocp.common.redis.lock;

import com.ocp.common.lock.DistributedLock;
import com.ocp.common.lock.OLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author kong
 * @date 2021/07/19 21:59
 * blog: http://blog.kongyin.ltd
 */
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "ocp.lock",name = "lockerType", havingValue = "REDIS", matchIfMissing = true)
public class RedissonDistributedLock implements DistributedLock {
    @Override
    public OLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) throws Exception {
        return null;
    }

    @Override
    public OLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws Exception {
        return null;
    }

    @Override
    public void unlock(Object lock) throws Exception {

    }
}
