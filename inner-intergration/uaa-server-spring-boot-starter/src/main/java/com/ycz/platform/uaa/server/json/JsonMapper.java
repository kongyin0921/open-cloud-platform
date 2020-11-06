package com.ycz.platform.uaa.server.json;

/**
 * @ClassName JsonMapper
 * @Description TODO
 * @Auther kongyin
 * @Date 2020/11/6 10:16
 **/
public interface JsonMapper {
    String write(Object input) throws Exception;

    <T> T read(String input, Class<T> type) throws Exception;
}
