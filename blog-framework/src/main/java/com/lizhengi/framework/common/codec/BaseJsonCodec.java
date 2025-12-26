package com.lizhengi.framework.common.codec;


import com.alibaba.fastjson2.JSON;

/**
 * @author lizhengi
 * @date 2025/12/22 10:51
 */
public abstract class BaseJsonCodec<T> implements JsonCodec<T> {

    private final Class<T> clazz;

    protected BaseJsonCodec(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String serialize(T obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj);
    }

    @Override
    public T deserialize(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }
}
