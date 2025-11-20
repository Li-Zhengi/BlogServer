package com.lizhengi.blog.pojo.vo;


import com.lizhengi.blog.pojo.bo.BlogArticleBO;
import com.lizhengi.blog.pojo.entity.BlogArticleEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/19 10:51
 */
public class BlogArticleListVO extends BlogArticleBO implements Serializable {

    public BlogArticleListVO(BlogArticleEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }

}
