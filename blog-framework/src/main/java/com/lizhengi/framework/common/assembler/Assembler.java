package com.lizhengi.framework.common.assembler;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lizhengi
 * @date 2025/12/22 10:09
 */
public interface Assembler<S, T> {

    T toTarget(S source);

    S toSource(T target);

    default List<T> toTargetList(List<S> sourceList) {
        if (sourceList == null) {
            return Collections.emptyList();
        }
        return sourceList.stream().map(this::toTarget).collect(Collectors.toList());
    }

    default List<S> toSourceList(List<T> targetList) {
        if (targetList == null) {
            return Collections.emptyList();
        }
        return targetList.stream().map(this::toSource).collect(Collectors.toList());
    }
}
