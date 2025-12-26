package com.lizhengi.system.pojo.bo;


import lombok.Data;

/**
 * 用户-业务对象
 *
 * @author lizhengi
 * @date 2025/11/20 10:04
 */
@Data
public class UserBO {

    /**
     * 用户：ID
     */
    private Long id;

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
