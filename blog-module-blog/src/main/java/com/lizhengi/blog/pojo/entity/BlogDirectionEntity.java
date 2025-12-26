package com.lizhengi.blog.pojo.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.blog.pojo.bo.BlogDirectionBO;
import com.lizhengi.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 分类
 *
 * @author lizhengi
 * @date 2025/11/10 09:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("blog_direction")
@NoArgsConstructor
public class BlogDirectionEntity extends BaseEntity<BlogDirectionEntity> {

    /**
     * JSON 构造方法
     * 通过 JSON 字符串直接创建 BlogDirectionEntity 实例
     *
     * @param jsonString JSON 字符串
     */
    public BlogDirectionEntity(String jsonString) {
        BeanUtils.copyProperties(JSON.parseObject(jsonString, BlogDirectionEntity.class), this);
    }

    public BlogDirectionEntity(BlogDirectionBO bo) {
        BeanUtils.copyProperties(bo, this);
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

    private String themeId;

    private String name;

    private String description;
}
