package com.ocp.common.redis.lock;

import com.ocp.common.constant.CommonConstant;
import com.ocp.common.exception.LockException;
import com.ocp.common.lock.DistributedLock;
import com.ocp.common.lock.OLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *
 * redisson分布式锁实现，基本锁功能的抽象实现
 * 本接口能满足绝大部分的需求，高级的锁功能，请自行扩展或直接使用原生api
 *
 * @author kong
 * @date 2021/07/19 21:59
 * blog: http://blog.kongyin.ltd
 */
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "ocp.lock",name = "lockerType", havingValue = "REDIS", matchIfMissing = true)
public class RedissonDistributedLock implements DistributedLock {
    @Autowired
    private RedissonClient redisson;

    private OLock getLock(String key, boolean isFair) {
        RLock lock;
        if (isFair) {
            lock = redisson.getFairLock(CommonConstant.LOCK_KEY_PREFIX + ":" + key);
        } else {
            lock =  redisson.getLock(CommonConstant.LOCK_KEY_PREFIX + ":" + key);
        }
        return new OLock(lock, this);
    }


    @Override
    public OLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) throws Exception {
        OLock olock = getLock(key, isFair);
        RLock lock = (RLock)olock.getLock();
        lock.lock(leaseTime,unit);
        return olock;
    }

    @Override
    public OLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws Exception {
        OLock olock = getLock(key, isFair);
        RLock lock = (RLock)olock.getLock();
        if (lock.tryLock(waitTime, leaseTime, unit)) {
            return olock;
        }
        return olock;
    }

    @Override
    public void unlock(Object lock) throws Exception {
        if (lock == null) {
            if (lock instanceof RLock) {
                RLock rLock = (RLock)lock;
                if (rLock.isLocked()) {
                    rLock.unlock();
                }
            } else {
                throw new LockException("requires RLock type");
            }
        }
    }
}
