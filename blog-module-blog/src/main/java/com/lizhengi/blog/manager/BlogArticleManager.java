package com.lizhengi.blog.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhengi.blog.mapper.BlogArticleMapper;
import com.lizhengi.blog.pojo.entity.BlogArticleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/10 10:09
 */
@Component
@RequiredArgsConstructor
public class BlogArticleManager extends ServiceImpl<BlogArticleMapper, BlogArticleEntity> {
}
