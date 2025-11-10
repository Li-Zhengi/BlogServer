package com.lizhengi.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lizhengi.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lizhengi
 * @date 2025/11/7 11:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("system_user")
public class UserEntity extends BaseEntity<UserEntity> {

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
