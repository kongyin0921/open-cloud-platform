package com.ycz.platform.redis.util;

/**
 * @ClassName RedisSubscribeCallback
 * @Description TODO
 * @Auther kongyin
 * @Date 2020/10/20 16:27
 **/
public interface RedisSubscribeCallback {
    void callback(String msg);
}