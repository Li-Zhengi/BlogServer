package com.lizhengi.framework.manager;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhengi.framework.common.assembler.BaseMultiAssembler;
import com.lizhengi.framework.common.codec.JsonCodec;
import com.lizhengi.framework.entity.BaseEntity;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * BaseCacheManager 是一个带缓存能力的通用 Manager 基类。
 * 它结合了 MyBatis-Plus 的 ServiceImpl 功能、Redis 缓存以及类型转换器（Assembler）：
 * - 支持单条对象缓存（CacheBO）
 * - 支持从 Entity 到 BO 的转换
 * - 支持 Json 序列化和反序列化缓存对象
 *
 * @param <M> Mapper 类型
 * @param <E> 实体类型，必须继承 BaseEntity
 * @param <B> BO 类型
 * @param <C> 缓存对象类型（CacheBO）
 * @param <A> Assembler 类型（Entity ↔ BO ↔ CacheBO）
 * @author lizhengi
 * @date 2025/12/23
 */
public abstract class BaseCacheManager<
        M extends BaseMapper<E>,
        E extends BaseEntity<E>,
        B,
        C,
        A extends BaseMultiAssembler<E, B, C>
        > extends ServiceImpl<M, E> {

    // ============================= 成员变量 =============================

    /**
     * Redis 操作模板
     */
    protected final StringRedisTemplate redisTemplate;

    /**
     * 缓存对象序列化/反序列化工具
     */
    protected final JsonCodec<C> cacheCodec;

    /**
     * 具体业务的 Assembler（Entity ↔ BO ↔ CacheBO）
     */
    protected final A assembler;

    // ============================= 构造方法 =============================

    /**
     * 构造函数
     *
     * @param redisTemplate Redis 操作模板
     * @param cacheCodec    缓存对象序列化/反序列化工具
     */
    protected BaseCacheManager(StringRedisTemplate redisTemplate, JsonCodec<C> cacheCodec, A assemblerBean) {
        this.redisTemplate = redisTemplate;
        this.cacheCodec = cacheCodec;
        this.assembler = assemblerBean;
    }

    // ============================= 抽象能力 =============================

    /**
     * @return 缓存名称
     */
    protected abstract String cacheName();

    /**
     * @return 缓存有效期
     */
    protected abstract Duration cacheTtl();

    // ============================= 单条缓存 =============================

    /**
     * 根据 ID 获取业务对象 BO
     *
     * @param id 实体 ID
     * @return 业务对象 BO，如果不存在返回 null
     */
    public B getBoById(Serializable id) {

        String key = cacheKey(id);

        // 1. 读取缓存 JSON
        String json = redisTemplate.opsForValue().get(key);
        // 2. 反序列化 JSON → CacheBO
        C cacheObject = cacheCodec.deserialize(json);

        if (ObjectUtil.isNotEmpty(cacheObject)) {
            // 3. 缓存命中，直接返回 BO
            return assembler.fromCache(cacheObject);
        }

        // 4. 缓存未命中，回源 DB 查询
        List<E> eList = getEntityByIdList(Collections.singletonList(id));
        if (CollUtil.isEmpty(eList) || ObjectUtil.isEmpty(eList.get(0))) {
            return null;
        }

        // 5. 回写缓存，并返回 BO
        return refreshBoCache(eList.get(0));
    }

    /**
     * 刷新单条对象缓存，并返回对应的 BO
     *
     * @param entity 实体对象
     * @return 业务对象 BO
     */
    public B refreshBoCache(E entity) {

        // 1. Entity → BO
        B bo = assembler.toBO(entity);
        // 2. BO → CacheBO
        C cacheBO = assembler.toCache(bo);
        // 3. CacheBO → JSON
        String cacheJson = cacheCodec.serialize(cacheBO);
        // 4. 写入 Redis 并设置 TTL
        redisTemplate.opsForValue().set(cacheKey(entity.getId()), cacheJson, cacheTtl());

        return bo;
    }

    // ============================= 批量缓存 =============================

    /**
     * 批量通过 ID 获取业务对象 BO
     *
     * @param idList 需要查询的实体 ID 列表（支持批量复用）
     * @return List<B> 业务对象列表 BO（缓存命中 + DB 兜底合并后的结果）
     */
    public List<B> batchGetBoByIdList(List<Serializable> idList) {

        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }

        // 1. 生成缓存 Keys
        List<String> keys = idList.stream().map(this::cacheKey).toList();

        // 2. 批量读取 Redis JSON（1 次网络 IO）
        List<String> jsonList = redisTemplate.opsForValue().multiGet(keys);

        List<B> result = new ArrayList<>();
        List<Serializable> missedIds = new ArrayList<>();

        // 3. 反序列化并组装 BO
        for (int i = 0; i < idList.size(); i++) {
            String json = (jsonList != null ? jsonList.get(i) : null);
            C cacheObject = cacheCodec.deserialize(json);

            if (cacheObject != null) {
                // Cache 命中：CacheBO → BO
                result.add(assembler.fromCache(cacheObject));
            } else {
                // 记录未命中 ID，等待 DB 兜底
                missedIds.add(idList.get(i));
            }
        }

        // 4. DB 兜底查询 Miss
        if (CollUtil.isNotEmpty(missedIds)) {
            List<E> entityList = getEntityByIdList(missedIds);

            // Entity 列表 → BO 列表
            List<B> bos = entityList.stream()
                    .filter(Objects::nonNull)
                    .map(assembler::toBO)
                    .toList();

            result.addAll(bos);

            // 批量回写缓存
            refreshBoCacheBatch(entityList);
        }

        return result;
    }

    /**
     * 批量回写缓存（适用于缓存重建、排行榜更新等）
     * 传入 Entity 列表，自动转换并写入 Redis
     */
    public void refreshBoCacheBatch(List<E> entityList) {
        if (CollUtil.isEmpty(entityList)) {
            return;
        }

        for (E entity : entityList) {
            if (entity == null) {
                continue;
            }

            // 1. Entity → BO
            B bo = assembler.toBO(entity);
            // 2. BO → CacheBO
            C cacheBO = assembler.toCache(bo);
            // 3. CacheBO → JSON
            String cacheJson = cacheCodec.serialize(cacheBO);
            // 4. 写入 Redis
            redisTemplate.opsForValue().set(cacheKey(entity.getId()), cacheJson, cacheTtl());
        }
    }


    // ============================= 5. 分页 ID 缓存能力 =============================


    /**
     * 直接使用外部传入的 QueryWrapper 进行分页 ID 缓存与查询
     *
     * @param page    页码（1开始）
     * @param size    每页大小
     * @param wrapper 调用方构建好的 QueryWrapper（可包含查询条件、排序、字段选择等）
     * @return 当前分页的 ID 列表（Serializable）
     */
    public List<Serializable> getPageIdList(int page, int size, QueryWrapper<E> wrapper) {
        if (wrapper == null) {
            return Collections.emptyList();
        }

        String prefix = listCacheKeyPrefix();
        String pageKey = prefix + ":wrapperHash:" + wrapper.hashCode() + ":page:" + page + ":size:" + size;

        // 1、读缓存
        List<String> cached = redisTemplate.opsForList().range(pageKey, 0, -1);
        if (CollUtil.isNotEmpty(cached)) {
            return cached.stream().map(id -> (Serializable) id).toList();
        }

        // 2、回源 DB
        wrapper.select("id");
        List<E> entities = super.list(wrapper);
        List<String> ids = entities.stream().map(e -> String.valueOf(e.getId())).toList();

        // 3、回写缓存
        rebuildPageIdCache(pageKey, ids, page, size);

        // 4、分页切片返回
        int from = (page - 1) * size;
        int to = Math.min(page * size, ids.size());
        if (from >= to) {
            return Collections.emptyList();
        }

        return ids.subList(from, to).stream().map(id -> (Serializable) id).toList();
    }


    /**
     * 回写分页 ID 缓存（Redis List），用于未来复用分页场景
     *
     * @param pageKey 缓存 Key
     * @param allIds  排序后的全量 ID 列表
     * @param page    当前页码
     * @param size    每页大小
     */
    protected void rebuildPageIdCache(String pageKey, List<String> allIds, int page, int size) {
        // 计算分页切片范围
        int from = (page - 1) * size;
        int to = Math.min(page * size, allIds.size());
        if (from >= to) {
            return;
        }

        // 取当前分页 ID 列表
        List<String> pageIds = allIds.subList(from, to);

        // 先删除旧缓存避免重复
        redisTemplate.delete(pageKey);

        // 逐条写入 Redis List（LPUSH/ RPUSH 任选，这里用 RPUSH 保持顺序）
        for (String id : pageIds) {
            redisTemplate.opsForList().rightPush(pageKey, id);
        }

        // 设置过期时间
        redisTemplate.expire(pageKey, cacheTtl());
    }

// ================== 单条增删改 ==================

    public B insertBO(B bo) {
        if (bo == null) {
            return null;
        }

        E entity = assembler.toEntity(bo);
        boolean success = super.save(entity);
        if (success) {
            return refreshBoCache(entity);
        }
        return null;
    }

    public B updateBO(B bo) {
        if (bo == null) {
            return null;
        }

        E entity = assembler.toEntity(bo);
        boolean success = super.updateById(entity);
        if (success) {
            return refreshBoCache(entity);
        }
        return null;
    }

    public boolean deleteBO(B bo) {
        if (bo == null) {
            return false;
        }

        E entity = assembler.toEntity(bo);
        boolean success = super.removeById(entity.getId());
        if (success) {
            redisTemplate.delete(cacheKey(entity.getId()));
        }
        return success;
    }

// ================== 批量增删改 ==================

    public List<B> batchInsertBO(List<B> boList) {
        if (CollUtil.isEmpty(boList)) {
            return Collections.emptyList();
        }

        List<E> entityList = boList.stream().map(assembler::toEntity).toList();
        boolean success = super.saveBatch(entityList);
        if (success) {
            refreshBoCacheBatch(entityList);
            return boList;
        }
        return Collections.emptyList();
    }

    public List<B> batchUpdateBO(List<B> boList) {
        if (CollUtil.isEmpty(boList)) {
            return Collections.emptyList();
        }

        List<E> entityList = boList.stream().map(assembler::toEntity).toList();
        boolean success = super.updateBatchById(entityList);
        if (success) {
            refreshBoCacheBatch(entityList);
            return boList;
        }
        return Collections.emptyList();
    }

    public boolean batchDeleteBO(List<B> boList) {
        if (CollUtil.isEmpty(boList)) {
            return false;
        }

        List<E> entityList = boList.stream().map(assembler::toEntity).toList();
        List<String> idList = entityList.stream().map(e -> String.valueOf(e.getId())).toList();

        boolean success = super.removeByIds(idList);
        if (success) {
            List<String> keys = idList.stream().map(this::cacheKey).toList();
            redisTemplate.delete(keys);
        }
        return success;
    }

    // ============================= 兜底查询 & 辅助方法 =============================

    /**
     * 通过 ID 列表批量查询数据库 Entity（不涉及缓存）
     *
     * @param idList 实体 ID 列表
     * @return List<E> 从数据库查询出的实体对象列表
     */
    public List<E> getEntityByIdList(List<Serializable> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<E> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(E::getId, idList);

        return super.list(queryWrapper);
    }

    /**
     * @return 单条对象缓存 Redis Key（根据实体 ID 生成）
     */
    protected String cacheKey(Serializable id){
        return "BLOG_SERVER" + ":" + cacheName() + ":" + id;
    }

    /**
     * @return 分页缓存 List Key 前缀
     */
    protected  String listCacheKeyPrefix(){
        return "BLOG_SERVER" + ":" + cacheName() + ":PREFIX";
    }
}
