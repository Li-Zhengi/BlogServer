package com.lizhengi.blog.system.service.impl;


import com.alibaba.fastjson2.JSON;
import com.lizhengi.blog.framework.config.security.SecurityUserService;
import com.lizhengi.blog.system.manager.OldUserManager;
import com.lizhengi.blog.system.manager.UserManager;
import com.lizhengi.blog.system.pojo.bo.UserBO;
import com.lizhengi.blog.system.pojo.entity.UserEntity;
import com.lizhengi.blog.system.pojo.vo.UserSelectVO;
import com.lizhengi.blog.system.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author lizhengi
 * @date 2025/12/23 15:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthServiceImpl implements UserAuthService, SecurityUserService {

    private final OldUserManager oldUserManager;

    private final UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("UserAuthServiceImpl#loadUserByUsername start, username:{}", username);

        // 从数据库或缓存查询用户
        UserBO userBO = userManager.getBoByUsername(username);
        if (userBO == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        log.info("UserAuthServiceImpl#loadUserByUsername -> userManager#getBoByUsername userBO:{}", JSON.toJSONString(userBO));

        // 构建权限列表
        // 数据库中 role 字段存储的是单个角色，例如 "ADMIN" 或 "USER"
        GrantedAuthority authority = new SimpleGrantedAuthority(userBO.getRole());

        // Spring Security 内部 User 对象
        UserDetails userDetails = User.builder()
                .username(userBO.getUsername())
                .password(userBO.getPassword())
                .authorities(Collections.singletonList(authority))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        log.info("UserAuthServiceImpl#loadUserByUsername end, userDetails:{}", userDetails);
        return userDetails;
    }

    @Override
    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {

        log.info("UserAuthServiceImpl#loadUserByUserId start, userId:{}", userId);

        // 从数据库或缓存查询用户
        UserBO userBO = userManager.getBoById(userId);
        if (userBO == null) {
            throw new UsernameNotFoundException("用户不存在: " + userId);
        }

        log.info("UserAuthServiceImpl#loadUserByUserId -> userManager#getBoByUsername userBO:{}", JSON.toJSONString(userBO));

        // 构建权限列表
        // 数据库中 role 字段存储的是单个角色，例如 "ADMIN" 或 "USER"
        GrantedAuthority authority = new SimpleGrantedAuthority(userBO.getRole());

        // Spring Security 内部 User 对象
        UserDetails userDetails = User.builder()
                .username(userBO.getUsername())
                .password(userBO.getPassword())
                .authorities(Collections.singletonList(authority))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        log.info("UserAuthServiceImpl#loadUserByUserId end, userDetails:{}", userDetails);
        return userDetails;
    }

    @Override
    public Long getUserIdByUsername(String username) {

        log.info("UserAuthServiceImpl#getUserIdByUsername start, username:{}", username);

        // 从数据库或缓存查询用户
        UserBO userBO = userManager.getBoByUsername(username);
        if (userBO == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        log.info("UserAuthServiceImpl#getUserIdByUsername -> userManager#getUserIdByUsername userBO:{}", JSON.toJSONString(userBO));

        Long userId = userBO.getId();

        log.info("UserAuthServiceImpl#getUserIdByUsername end, userId:{}", userId);
        return userId;
    }

    public UserSelectVO selectUserByUsername(String username){
        // 从数据库或缓存查询用户
        UserEntity userEntity = oldUserManager.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        return new UserSelectVO(userEntity);
    }
}
