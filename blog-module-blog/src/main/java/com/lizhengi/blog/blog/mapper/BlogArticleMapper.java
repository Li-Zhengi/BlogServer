package com.lizhengi.blog.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhengi.blog.blog.pojo.entity.BlogArticleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author lizhengi
 * @date 2025/11/10 10:05
 */
@Repository
@Mapper
public interface BlogArticleMapper extends BaseMapper<BlogArticleEntity> {
}
