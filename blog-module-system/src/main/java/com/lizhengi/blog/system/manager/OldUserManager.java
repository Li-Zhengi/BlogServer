package com.lizhengi.blog.system.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lizhengi.blog.framework.manager.BaseCacheIdManager;
import com.lizhengi.blog.system.mapper.UserMapper;
import com.lizhengi.blog.system.pojo.entity.UserEntity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/7 11:32
 */
@Component
public class OldUserManager extends BaseCacheIdManager<UserMapper, UserEntity> {

    private static final String CACHE_KEY_PREFIX = "BlogServer:Blog:User:";

    public OldUserManager(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX;
    }

    @Override
    protected Long getId(UserEntity entity) {
        return entity.getId();
    }

    @Override
    protected void setId(UserEntity entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected UserEntity buildEntityFromCache(String cacheValue) {
        return new UserEntity(cacheValue);
    }

    public UserEntity getByUsername(String username) {

        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername, username);


        UserEntity userEntity = this.baseMapper.selectOne(queryWrapper);

        System.out.println(userEntity);
        // 可以先从缓存查，再查数据库
        return userEntity;
    }
}
