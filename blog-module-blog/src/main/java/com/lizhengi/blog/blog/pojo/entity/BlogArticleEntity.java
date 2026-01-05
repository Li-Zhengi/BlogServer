package com.lizhengi.blog.blog.pojo.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.blog.blog.pojo.bo.BlogArticleBO;
import com.lizhengi.blog.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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
@NoArgsConstructor
public class BlogArticleEntity extends BaseEntity<BlogArticleEntity> {

    /**
     * JSON 构造方法
     * 通过 JSON 字符串直接创建 ActivityEntity 实例
     *
     * @param jsonString JSON 字符串
     */
    public BlogArticleEntity(String jsonString) {
        BeanUtils.copyProperties(JSON.parseObject(jsonString, BlogArticleEntity.class), this);
    }

    public BlogArticleEntity(BlogArticleBO bo) {
        BeanUtils.copyProperties(bo, this);
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

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
