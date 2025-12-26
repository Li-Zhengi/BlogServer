package com.lizhengi.framework.common.codec;


/**
 * @author lizhengi
 * @date 2025/12/22 10:18
 */
public interface JsonCodec<T> {

    String serialize(T obj);

    T deserialize(String json);
}
