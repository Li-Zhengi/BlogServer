package com.lizhengi.blog.blog.manager;

import com.lizhengi.blog.blog.mapper.BlogDirectionMapper;
import com.lizhengi.blog.blog.pojo.entity.BlogDirectionEntity;
import com.lizhengi.blog.framework.manager.BaseCacheIdManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/10 10:10
 */
@Component
public class BlogDirectionManager extends BaseCacheIdManager<BlogDirectionMapper, BlogDirectionEntity> {

    private static final String CACHE_KEY_PREFIX = "BlogServer:Blog:direction:";

    public BlogDirectionManager(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX;
    }

    @Override
    protected Long getId(BlogDirectionEntity entity) {
        return entity.getId();
    }

    @Override
    protected void setId(BlogDirectionEntity entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected BlogDirectionEntity buildEntityFromCache(String cacheValue) {
        return new BlogDirectionEntity(cacheValue);
    }
}
