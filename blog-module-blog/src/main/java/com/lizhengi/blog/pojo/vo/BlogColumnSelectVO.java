package com.lizhengi.blog.pojo.vo;


import com.lizhengi.blog.pojo.bo.BlogColumnBO;
import com.lizhengi.blog.pojo.entity.BlogColumnEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/19 10:49
 */
public class BlogColumnSelectVO extends BlogColumnBO implements Serializable {

    public BlogColumnSelectVO(BlogColumnEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
