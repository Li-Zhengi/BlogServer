package com.lizhengi.blog.system.pojo.bo;


import lombok.Data;

/**
 * @author lizhengi
 * @date 2025/12/26 09:59
 */
@Data
public class UserCacheBO {

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
