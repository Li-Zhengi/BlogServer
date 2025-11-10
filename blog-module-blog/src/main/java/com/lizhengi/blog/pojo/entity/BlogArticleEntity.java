package com.lizhengi.blog.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 文章
 *
 * @author lizhengi
 * @date 2025/11/10 09:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_article")
public class BlogArticleEntity extends BaseEntity<BlogArticleEntity> {

    /**
     * 文章名称
     */
    private String name;

    /**
     * 文章封面
     */
    private String coverImg;

    /**
     * 文章作者 ID
     */
    private String authorId;

    /**
     * 专栏 ID
     */
    private String columnId;

    /**
     * 文章发布时间
     */
    private Date releaseDateTime;

    /**
     * 文章修改时间
     */
    private Date modifyDateTime;

    /**
     * 文章内容链接
     */
    private String articleContentHtmlUrl;

    /**
     * 文章内容链接
     */
    private String articleContentMdUrl;

    /**
     * 文章状态
     */
    private String status;

    /**
     * 浏览量
     */
    private long views;

    /**
     * 精选
     */
    private boolean selection;
}
