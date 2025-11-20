package com.lizhengi.blog.manager;

import com.lizhengi.blog.mapper.BlogColumnMapper;
import com.lizhengi.blog.pojo.entity.BlogColumnEntity;
import com.lizhengi.manager.BaseCacheIdManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/10 10:09
 */
@Component
public class BlogColumnManager extends BaseCacheIdManager<BlogColumnMapper, BlogColumnEntity> {

    private static final String CACHE_KEY_PREFIX = "BlogServer:Blog:column:";

    public BlogColumnManager(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX;
    }

    @Override
    protected String getId(BlogColumnEntity entity) {
        return entity.getId();
    }

    @Override
    protected void setId(BlogColumnEntity entity, String id) {
        entity.setId(id);
    }

    @Override
    protected BlogColumnEntity buildEntityFromCache(String cacheValue) {
        return new BlogColumnEntity(cacheValue);
    }
}
