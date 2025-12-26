package com.lizhengi.framework.common.assembler;


/**
 * @author lizhengi
 * @date 2025/12/23 16:39
 */
public class BaseMultiAssembler<E, B, C> {

    private final BaseAssembler<E, B> entityBoAssembler;

    private final BaseAssembler<B, C> boCacheAssembler;

    public BaseMultiAssembler(BaseAssembler<E, B> entityBoAssembler, BaseAssembler<B, C> boCacheAssembler) {
        this.entityBoAssembler = entityBoAssembler;
        this.boCacheAssembler = boCacheAssembler;
    }

    public B toBO(E entity) {
        return entityBoAssembler.toTarget(entity);
    }

    public E toEntity(B bo) {
        return entityBoAssembler.toSource(bo);
    }

    public C toCache(B bo) {
        return boCacheAssembler.toTarget(bo);
    }

    public B fromCache(C cache) {
        return boCacheAssembler.toSource(cache);
    }
}
