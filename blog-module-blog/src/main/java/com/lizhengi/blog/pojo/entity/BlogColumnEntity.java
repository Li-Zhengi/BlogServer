package com.lizhengi.blog.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 专栏
 *
 * @author lizhengi
 * @date 2025/11/10 09:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_column")
public class BlogColumnEntity extends BaseEntity<BlogColumnEntity> {

    private String themeId;

    private String directionId;

    private String name;

    private String coverImg;

    private String description;
}
