package com.lizhengi.blog.blog.manager;


import com.lizhengi.blog.blog.mapper.BlogArticleMapper;
import com.lizhengi.blog.blog.pojo.entity.BlogArticleEntity;
import com.lizhengi.blog.framework.manager.BaseCacheIdManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/10 10:09
 */
@Component
public class BlogArticleManager extends BaseCacheIdManager<BlogArticleMapper, BlogArticleEntity> {

    private static final String CACHE_KEY_PREFIX = "BlogServer:Blog:Article:";

    public BlogArticleManager(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX;
    }

    @Override
    protected Long getId(BlogArticleEntity entity) {
        return entity.getId();
    }

    @Override
    protected void setId(BlogArticleEntity entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected BlogArticleEntity buildEntityFromCache(String cacheValue) {
        return new BlogArticleEntity(cacheValue);
    }
}
