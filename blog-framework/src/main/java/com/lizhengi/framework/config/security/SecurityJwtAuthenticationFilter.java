package com.lizhengi.framework.config.security;

import cn.hutool.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * @author lizhengi
 * @date 2025/11/27 10:58
 */
@Component
@RequiredArgsConstructor
public class SecurityJwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityUserService service;


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 从请求头读取 Token
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                // 解析 Token，获取 JWT
                JWT jwt = JWT.of(token);

                // 取出 payload 中的 id
                String username = jwt.getPayload("userName").toString();

                // 查询用户
                UserDetails userDetails = service.loadUserByUsername(username);

                // 构建认证对象
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 放入 SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception ignored) {
                // Token 非法，继续走，让后面的 EntryPoint 处理
            }
        }

        filterChain.doFilter(request, response);
    }
}