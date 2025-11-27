package com.lizhengi.system.config;

import com.lizhengi.system.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import cn.hutool.jwt.JWT;


/**
 * @author lizhengi
 * @date 2025/11/27 10:58
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                // 解析 Token
                JWT jwt = JWT.of(token);
                String username = jwt.getPayload("username").toString();

                // 查询用户
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

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