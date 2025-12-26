package com.lizhengi.system.service.impl;


import com.alibaba.fastjson2.JSON;
import com.lizhengi.system.manager.OldUserManager;
import com.lizhengi.system.pojo.entity.UserEntity;
import com.lizhengi.system.pojo.vo.UserSelectVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author lizhengi
 * @date 2025/11/25 17:01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final OldUserManager userManager;

    /**
     * 根据用户名查询用户信息
     *
     * @param username 前端提交的用户名
     * @return UserDetails 包含用户名、密码、权限
     * @throws UsernameNotFoundException 用户不存在抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 从数据库或缓存查询用户
        UserEntity userEntity = userManager.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        log.info("userEntity:{}", JSON.toJSONString(userEntity));

        // 构建权限列表
        // 数据库中 role 字段存储的是单个角色，例如 "ADMIN" 或 "USER"
        GrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole());

        // 返回 Spring Security 内部 User 对象
        return User.builder()
                .username(userEntity.getUsername())
                // 数据库加密后的密码
                .password(userEntity.getPassword())
                .authorities(Collections.singletonList(authority))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public UserSelectVO selectUserByUsername(String username){
        // 从数据库或缓存查询用户
        UserEntity userEntity = userManager.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        return new UserSelectVO(userEntity);
    }

}
