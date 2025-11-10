package com.lizhengi.blog.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类
 *
 * @author lizhengi
 * @date 2025/11/10 09:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_direction")
public class BlogDirectionEntity extends BaseEntity<BlogDirectionEntity> {

    private String themeId;

    private String name;

    private String description;
}
