package com.lizhengi.blog.framework.common.assembler;


import org.springframework.beans.BeanUtils;

/**
 * @author lizhengi
 * @date 2025/12/22 10:10
 */
public abstract class BaseAssembler<S, T> implements Assembler<S, T> {

    protected abstract T createTarget();

    protected abstract S createSource();

    protected void copyToTarget(S source, T target) {
        BeanUtils.copyProperties(source, target);
    }

    protected void copyToSource(T target, S source) {
        BeanUtils.copyProperties(target, source);
    }

    @Override
    public T toTarget(S source) {
        if (source == null) {
            return null;
        }
        T target = createTarget();
        copyToTarget(source, target);
        return target;
    }

    @Override
    public S toSource(T target) {
        if (target == null) {
            return null;
        }
        S source = createSource();
        copyToSource(target, source);
        return source;
    }
}
