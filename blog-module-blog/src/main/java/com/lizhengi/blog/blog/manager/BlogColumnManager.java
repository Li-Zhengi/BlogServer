package com.lizhengi.blog.blog.manager;

import com.lizhengi.blog.blog.mapper.BlogColumnMapper;
import com.lizhengi.blog.blog.pojo.entity.BlogColumnEntity;
import com.lizhengi.blog.framework.manager.BaseCacheIdManager;
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
    protected Long getId(BlogColumnEntity entity) {
        return entity.getId();
    }

    @Override
    protected void setId(BlogColumnEntity entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected BlogColumnEntity buildEntityFromCache(String cacheValue) {
        return new BlogColumnEntity(cacheValue);
    }
}
