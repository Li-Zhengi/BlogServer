package com.lizhengi.blog.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhengi.blog.mapper.BlogColumnMapper;
import com.lizhengi.blog.pojo.entity.BlogColumnEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/10 10:09
 */
@Component
@RequiredArgsConstructor
public class BlogColumnManager extends ServiceImpl<BlogColumnMapper, BlogColumnEntity> {
}
