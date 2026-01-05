package com.lizhengi.blog.framework.config.mybatis.plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author lizhengi
 * @date 2025/11/7 11:12
 */
@Component
@Primary
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * 插入时自动填充字段
     *
     * @param metaObject 当前操作对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Instant now = Instant.now();
        // 使用当前时间填充创建时间
        fillValIfNullByName("createTime", now, metaObject);
        // 使用当前时间填充更新时间
        fillValIfNullByName("updateTime", now, metaObject);
    }

    /**
     * 更新时自动填充字段
     *
     * @param metaObject 当前操作对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Instant now = Instant.now();
        // 更新时间使用当前时间
        fillValIfNullByName("updateTime", now, metaObject);
    }

    /**
     * 填充字段值，如果用户已手动设置则保持原值
     *
     * @param fieldName  字段名
     * @param fieldVal   填充值
     * @param metaObject 当前操作对象
     */
    private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (fieldVal == null || metaObject == null) {
            return;
        }

        // 判断对象是否有 setter 方法
        if (!metaObject.hasSetter(fieldName)) {
            return;
        }

        // 判断用户是否已手动设置值
        Object currentValue = metaObject.getValue(fieldName);
        if (currentValue != null && !currentValue.toString().isBlank()) {
            return; // 已有值，不覆盖
        }

        // 判断类型兼容性，确保 fieldVal 可赋值给 getter 类型
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (getterType != null && getterType.isInstance(fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }
}
