package com.lizhengi.blog.blog.pojo.vo;


import com.lizhengi.blog.blog.pojo.bo.BlogColumnBO;
import com.lizhengi.blog.blog.pojo.entity.BlogColumnEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/19 10:50
 */
public class BlogColumnListVO extends BlogColumnBO implements Serializable {

    public BlogColumnListVO(BlogColumnEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}

