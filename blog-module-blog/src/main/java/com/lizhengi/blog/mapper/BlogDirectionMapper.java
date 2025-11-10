package com.lizhengi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhengi.blog.pojo.entity.BlogDirectionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author lizhengi
 * @date 2025/11/10 10:06
 */
@Repository
@Mapper
public interface BlogDirectionMapper extends BaseMapper<BlogDirectionEntity> {
}
