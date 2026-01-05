package com.lizhengi.blog.blog.pojo.vo;


import com.lizhengi.blog.blog.pojo.bo.BlogArticleBO;
import com.lizhengi.blog.blog.pojo.entity.BlogArticleEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/19 10:50
 */
public class BlogArticleSelectVO extends BlogArticleBO implements Serializable {

    public BlogArticleSelectVO(BlogArticleEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }

}
