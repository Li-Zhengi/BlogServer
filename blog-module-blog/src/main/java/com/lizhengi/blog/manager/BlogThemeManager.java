package com.lizhengi.blog.manager;


import com.lizhengi.blog.mapper.BlogThemeMapper;
import com.lizhengi.blog.pojo.entity.BlogThemeEntity;
import com.lizhengi.framework.manager.BaseCacheIdManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/10 10:10
 */
@Component
public class BlogThemeManager extends BaseCacheIdManager<BlogThemeMapper, BlogThemeEntity> {

    private static final String CACHE_KEY_PREFIX = "BlogServer:Blog:theme:";

    public BlogThemeManager(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX;
    }

    @Override
    protected String getId(BlogThemeEntity entity) {
        return entity.getId();
    }

    @Override
    protected void setId(BlogThemeEntity entity, String id) {
        entity.setId(id);
    }

    @Override
    protected BlogThemeEntity buildEntityFromCache(String cacheValue) {
        return new BlogThemeEntity(cacheValue);
    }
}
