package com.lizhengi.framework.common.codec;


/**
 * @author lizhengi
 * @date 2025/12/22 10:51
 */
public class SimpleJsonCodec<T> extends BaseJsonCodec<T> {

    public SimpleJsonCodec(Class<T> clazz) {
        super(clazz);
    }
}