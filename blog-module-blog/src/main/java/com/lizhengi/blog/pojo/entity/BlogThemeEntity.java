package com.lizhengi.blog.pojo.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.blog.pojo.bo.BlogThemeBO;
import com.lizhengi.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * 主题
 *
 * @author lizhengi
 * @date 2025/11/10 09:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_theme")
public class BlogThemeEntity extends BaseEntity<BlogThemeEntity> {

    /**
     * JSON 构造方法
     * 通过 JSON 字符串直接创建 BlogThemeEntity 实例
     *
     * @param jsonString JSON 字符串
     */
    public BlogThemeEntity(String jsonString) {
        BeanUtils.copyProperties(JSON.parseObject(jsonString, BlogThemeEntity.class), this);
    }

    public BlogThemeEntity(BlogThemeBO bo) {
        BeanUtils.copyProperties(bo, this);
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

    private String name;

    private String description;
}