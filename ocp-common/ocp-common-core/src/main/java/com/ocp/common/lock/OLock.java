package com.ocp.common.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 锁对象抽象
 * @author kong
 * @date 2021/07/18 20:58
 * blog: http://blog.kongyin.ltd
 */
@AllArgsConstructor
public class OLock implements AutoCloseable{
    @Getter
    private final Object lock;

    private final DistributedLock locker;

    @Override
    public void close() throws Exception {
        locker.unlock(lock);
    }
}
