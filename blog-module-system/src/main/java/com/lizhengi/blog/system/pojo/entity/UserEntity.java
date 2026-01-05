package com.lizhengi.blog.system.pojo.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.blog.framework.entity.BaseEntity;
import com.lizhengi.blog.system.pojo.bo.UserBO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author lizhengi
 * @date 2025/11/7 11:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("system_user")
@NoArgsConstructor
public class UserEntity extends BaseEntity<UserEntity> {

    /**
     * JSON 构造方法
     * 通过 JSON 字符串直接创建 BlogColumnEntity 实例
     *
     * @param jsonString JSON 字符串
     */
    public UserEntity(String jsonString) {
        BeanUtils.copyProperties(JSON.parseObject(jsonString, UserEntity.class), this);
    }

    public UserEntity(UserBO bo) {
        BeanUtils.copyProperties(bo, this);
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }


    /**
     * 用户：用户名
     */
    private String username;

    /**
     * 用户：密码
     */
    private String password;

    /**
     * 用户：昵称
     */
    private String nickname;

    /**
     * 用户：头像
     */
    private String avatarUrl;

    /**
     * 用户：角色
     */
    private String role;
}
