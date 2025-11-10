package com.lizhengi.blog.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhengi.blog.mapper.BlogThemeMapper;
import com.lizhengi.blog.pojo.entity.BlogThemeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/10 10:10
 */
@Component
@RequiredArgsConstructor
public class BlogThemeManager extends ServiceImpl<BlogThemeMapper, BlogThemeEntity> {
}
