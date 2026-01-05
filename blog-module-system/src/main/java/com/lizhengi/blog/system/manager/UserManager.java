package com.lizhengi.blog.system.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lizhengi.blog.framework.common.codec.SimpleJsonCodec;
import com.lizhengi.blog.framework.manager.BaseCacheManager;
import com.lizhengi.blog.system.mapper.UserMapper;
import com.lizhengi.blog.system.pojo.assembler.UserAssembler;
import com.lizhengi.blog.system.pojo.bo.UserBO;
import com.lizhengi.blog.system.pojo.bo.UserCacheBO;
import com.lizhengi.blog.system.pojo.entity.UserEntity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author lizhengi
 * @date 2025/12/26 09:57
 */
@Component
public class UserManager extends BaseCacheManager<UserMapper, UserEntity, UserBO, UserCacheBO, UserAssembler> {

    /**
     * 构造函数
     *
     * @param redisTemplate Redis 操作模板
     */
    protected UserManager(StringRedisTemplate redisTemplate) {
        super(redisTemplate, new SimpleJsonCodec<>(UserCacheBO.class), new UserAssembler());
    }

    @Override
    protected String cacheName() {
        return "USER";
    }

    @Override
    protected Duration cacheTtl() {
        return Duration.ofHours(1);
    }

    /**
     * 根据用户名查用户信息
     *
     * @param username 用户名
     * @return 当前用户名对应的信息
     */
    public UserBO getBoByUsername(String username) {
        // 1. 只查 id
        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
        query.eq(UserEntity::getUsername, username)
                .select(UserEntity::getId);

        UserEntity entity = this.baseMapper.selectOne(query);
        if (entity == null) {
            return null;
        }

        Long id = entity.getId();

        // 2. 先走缓存再查 DB（父类已统一处理 assembler 和 codec）
        return this.getBoById(id);
    }

}
