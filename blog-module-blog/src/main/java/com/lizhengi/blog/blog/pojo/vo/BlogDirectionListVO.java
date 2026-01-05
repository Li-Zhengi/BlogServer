package com.lizhengi.blog.blog.pojo.vo;


import com.lizhengi.blog.blog.pojo.bo.BlogDirectionBO;
import com.lizhengi.blog.blog.pojo.entity.BlogDirectionEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/19 10:48
 */
public class BlogDirectionListVO extends BlogDirectionBO implements Serializable {

    public BlogDirectionListVO(BlogDirectionEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
