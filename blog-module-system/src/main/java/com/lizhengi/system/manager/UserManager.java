package com.lizhengi.system.manager;


import com.lizhengi.manager.BaseCacheIdManager;
import com.lizhengi.system.mapper.UserMapper;
import com.lizhengi.system.pojo.entity.UserEntity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/7 11:32
 */
@Component
public class UserManager extends BaseCacheIdManager<UserMapper, UserEntity> {

    private static final String CACHE_KEY_PREFIX = "BlogServer:Blog:User:";

    public UserManager(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX;
    }

    @Override
    protected String getId(UserEntity entity) {
        return entity.getId();
    }

    @Override
    protected void setId(UserEntity entity, String id) {
        entity.setId(id);
    }

    @Override
    protected UserEntity buildEntityFromCache(String cacheValue) {
        return new UserEntity(cacheValue);
    }
}
