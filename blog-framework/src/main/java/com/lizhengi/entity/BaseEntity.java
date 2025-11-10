package com.lizhengi.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author lizhengi
 * @date 2025/11/7 11:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseEntity<T extends Model<T>> extends Model<T> {

    @TableId
    private String id;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Integer deleteId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
