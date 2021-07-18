package com.ocp.common.zookeeper.lock;

import com.ocp.common.constant.CommonConstant;
import com.ocp.common.exception.LockException;
import com.ocp.common.lock.DistributedLock;
import com.ocp.common.lock.OLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper分布式锁实现
 * @author kong
 * @date 2021/07/16 22:05
 * blog: http://blog.kongyin.ltd
 */
@Component
@ConditionalOnProperty(prefix = "ocp.lock", name = "lockerType", havingValue = "ZK")
public class ZookeeperDistributedLock implements DistributedLock {
    @Resource
    private CuratorFramework client;

    private OLock getLock(String key) {
        InterProcessMutex lock = new InterProcessMutex(client, getPath(key));
        return new OLock(lock, this);
    }

    @Override
    public OLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) throws Exception {
        OLock oLock = this.getLock(key);
        InterProcessMutex ipm = (InterProcessMutex)oLock.getLock();
        ipm.acquire();
        return oLock;
    }

    @Override
    public OLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws Exception {
        OLock oLock = this.getLock(key);
        InterProcessMutex ipm = (InterProcessMutex)oLock.getLock();
        if (ipm.acquire(waitTime, unit)) {
            return oLock;
        }
        return null;
    }

    @Override
    public void unlock(Object lock) throws Exception {
        if (lock != null) {
            if (lock instanceof InterProcessMutex) {
                InterProcessMutex ipm = (InterProcessMutex)lock;
                if (ipm.isAcquiredInThisProcess()) {
                    ipm.release();
                }
            } else {
                throw new LockException("requires InterProcessMutex type");
            }
        }
    }

    private String getPath(String key) {
        return CommonConstant.PATH_SPLIT + CommonConstant.LOCK_KEY_PREFIX + CommonConstant.PATH_SPLIT + key;
    }
}
