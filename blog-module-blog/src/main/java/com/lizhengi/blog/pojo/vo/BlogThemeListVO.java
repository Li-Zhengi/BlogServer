package com.lizhengi.blog.pojo.vo;


import com.lizhengi.blog.pojo.bo.BlogThemeBO;
import com.lizhengi.blog.pojo.entity.BlogThemeEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/19 10:47
 */
public class BlogThemeListVO extends BlogThemeBO implements Serializable {

    public BlogThemeListVO(BlogThemeEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
