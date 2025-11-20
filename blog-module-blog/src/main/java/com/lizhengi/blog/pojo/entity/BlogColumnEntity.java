package com.lizhengi.blog.pojo.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.blog.pojo.bo.BlogColumnBO;
import com.lizhengi.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

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

    /**
     * JSON 构造方法
     * 通过 JSON 字符串直接创建 BlogColumnEntity 实例
     *
     * @param jsonString JSON 字符串
     */
    public BlogColumnEntity(String jsonString) {
        BeanUtils.copyProperties(JSON.parseObject(jsonString, BlogColumnEntity.class), this);
    }

    public BlogColumnEntity(BlogColumnBO bo) {
        BeanUtils.copyProperties(bo, this);
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

    private String themeId;

    private String directionId;

    private String name;

    private String coverImg;

    private String description;
}
