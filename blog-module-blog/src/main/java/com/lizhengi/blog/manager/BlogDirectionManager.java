package com.lizhengi.blog.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhengi.blog.mapper.BlogDirectionMapper;
import com.lizhengi.blog.pojo.entity.BlogDirectionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/10 10:10
 */
@Component
@RequiredArgsConstructor
public class BlogDirectionManager extends ServiceImpl<BlogDirectionMapper, BlogDirectionEntity> {
}
