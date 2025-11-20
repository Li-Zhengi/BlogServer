package com.lizhengi.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lizhengi
 * @date 2025/11/18 10:27
 */
@Component
@RequiredArgsConstructor
public abstract class BaseCacheIdManager<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    protected final StringRedisTemplate stringRedisTemplate;

    /**
     * 业务前缀（由子类实现）
     * 如：BlogServer:blog:column:
     */
    protected abstract String getCacheKeyPrefix();

    /**
     * 从实体中获取 ID
     */
    protected abstract String getId(T entity);

    /**
     * 设置 ID
     */
    protected abstract void setId(T entity, String id);

    /**
     * 构造实体（从缓存字符串反序列化）
     */
    protected abstract T buildEntityFromCache(String cacheValue);

    /**
     * 自增 ID 生成（Redis + DB 兜底）
     */
    protected String getNextId() {
        String redisKey = getCacheKeyPrefix() + "id";

        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(redisKey))) {

            // 数据库查最大 ID
            QueryWrapper<T> qw = new QueryWrapper<>();
            qw.select("id").orderByDesc("id").last("LIMIT 1");

            Long maxId = baseMapper.selectObjs(qw).stream()
                    .map(id -> {
                        try { return Long.parseLong(String.valueOf(id)); }
                        catch (Exception e) { return 1L; }
                    })
                    .findFirst()
                    .orElse(1L);

            stringRedisTemplate.opsForValue().set(redisKey, String.valueOf(maxId), 24, TimeUnit.HOURS);
        }

        Long newId = stringRedisTemplate.opsForValue().increment(redisKey);
        if (newId == null) {
            throw new RuntimeException("Redis INCR 失败：" + redisKey);
        }
        return String.valueOf(newId);
    }

    // ================================== 新增 ==================================
    public void create(T entity) {
        setId(entity, getNextId());
        save(entity);

        stringRedisTemplate.opsForValue()
                .set(getCacheKeyPrefix() + getId(entity),
                        entity.toString(), 24, TimeUnit.HOURS);
    }

    // ================================== 删除 ==================================
    public void delete(String id) {
        baseMapper.deleteById(id);
        stringRedisTemplate.delete(getCacheKeyPrefix() + id);
    }

    // ================================== 更新 ==================================
    public void updateEntity(T entity) {
        baseMapper.updateById(entity);

        stringRedisTemplate.opsForValue()
                .set(getCacheKeyPrefix() + getId(entity),
                        entity.toString(), 24, TimeUnit.HOURS);
    }

    // ================================== 查询 ==================================
    public T select(String id) {
        String key = getCacheKeyPrefix() + id;

        String cacheValue = stringRedisTemplate.opsForValue().get(key);
        if (cacheValue != null) {
            return buildEntityFromCache(cacheValue);
        }

        T entity = baseMapper.selectById(id);
        if (entity != null) {
            stringRedisTemplate.opsForValue().set(key, entity.toString(), 24, TimeUnit.HOURS);
        }
        return entity;
    }

    // ================================== 列表 ==================================
    public List<T> listByEntity(LambdaQueryWrapper<T> wrapper) {
        return this.list(wrapper);
    }

    // ================================== 分页 ==================================
    public Page<T> pageQuery(LambdaQueryWrapper<T> wrapper, int pageNum, int pageSize) {
        Page<T> page = new Page<>(pageNum, pageSize);
        return this.baseMapper.selectPage(page, wrapper);
    }
}
