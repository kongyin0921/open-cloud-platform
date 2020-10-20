package com.ycz.platform.redis.prefix;

/**
 * @ClassName KeyPrefix
 * @Description key前缀
 * @Auther kongyin
 * @Date 2020/10/20 16:39
 **/
public interface KeyPrefix {
    /**
     * 过期描述
     */
    public int expireSeconds();

    /**
     * 前缀
     */
    public String getPrefix();
}
