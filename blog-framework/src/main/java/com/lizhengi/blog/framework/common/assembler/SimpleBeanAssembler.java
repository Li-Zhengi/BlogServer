package com.lizhengi.blog.framework.common.assembler;


import java.util.function.Supplier;

/**
 * @author lizhengi
 * @date 2025/12/22 10:13
 */
public class SimpleBeanAssembler<S, T> extends BaseAssembler<S, T> {

    private final Supplier<S> sourceSupplier;
    private final Supplier<T> targetSupplier;

    public SimpleBeanAssembler(Supplier<S> sourceSupplier,
                               Supplier<T> targetSupplier) {
        this.sourceSupplier = sourceSupplier;
        this.targetSupplier = targetSupplier;
    }

    @Override
    protected S createSource() {
        return sourceSupplier.get();
    }

    @Override
    protected T createTarget() {
        return targetSupplier.get();
    }
}
