package com.lizhengi.blog.framework.config.security;


import cn.hutool.jwt.JWT;
import com.lizhengi.blog.framework.common.constant.SystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 统一处理认证响应回调：
 * - 登录成功（200 + 返回 JWT Token）
 * - 登录失败（401 + 返回错误 JSON）
 *
 * @author lizhengi
 * @date 2025/12/26 15:06
 */
@Component
@RequiredArgsConstructor
public class CustomAuthResponseHandler {

    /**
     * 注入自定义用户认证服务（实现了 Spring Security 的 UserDetailsService）
     * 用于根据用户名查询用户信息、密码、权限等
     */
    private final SecurityUserService service;

    /**
     * 登录成功回调处理器
     *
     * @return AuthenticationSuccessHandler（Lambda 实现）
     */
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            response.setContentType("application/json;charset=UTF-8");
            String username = authentication.getName();

            // 根据用户名查询用户 ID（Token 验证使用 ID 验证）
            Long userId = service.getUserIdByUsername(username);

            String token = JWT.create()
                    .setPayload("username", username)
                    .setPayload("userId", userId)
                    .setExpiresAt(new Date(System.currentTimeMillis() + 3600_000))
                    .setKey(SystemConstant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8))
                    .sign();

            response.getWriter().write("{\"code\":0,\"token\":\"" + token + "\"}");
        };
    }


    /**
     * 登录失败回调处理器
     *
     * @return AuthenticationFailureHandler（Lambda 实现）
     */
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"用户名或密码错误\"}");
        };
    }
}
