package com.ycz.platform.redis.serializer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @ClassName RedisObjectSerializer
 * @Description 此时定义的序列化操作表示可以序列化所有类的对象，当然，这个对象所在的类一定要实现序列化接口
 * @Auther kongyin
 * @Date 2020/10/20 16:28
 **/

public class RedisObjectSerializer implements RedisSerializer<Object> {

    /**
     * 为了方便进行对象与字节数组的转换，所以应该首先准备出两个转换器
     */
    private Converter<Object, byte[]> serializingConverter = new SerializingConverter();
    private Converter<byte[], Object> deserializingConverter = new DeserializingConverter();
    /**
     * 做一个空数组，不是null
     */
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if(o == null){
            return EMPTY_BYTE_ARRAY;
        }else {
            //将对象变为字节数组
            return this.serializingConverter.convert(o);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        // 此时没有对象的内容信息
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return this.deserializingConverter.convert(bytes);
    }
}
