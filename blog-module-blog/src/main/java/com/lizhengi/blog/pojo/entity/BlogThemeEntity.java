package com.lizhengi.blog.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 主题
 *
 * @author lizhengi
 * @date 2025/11/10 09:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_theme")
public class BlogThemeEntity extends BaseEntity<BlogThemeEntity> {

    private String name;

    private String description;
}
