package com.lizhengi.blog.framework.config.security;

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
 * JWT 解析过滤器（每个请求执行一次），参数：HttpServletRequest / HttpServletResponse / FilterChain（由 OncePerRequestFilter 内部控制）
 *
 * @author lizhengi
 * @date 2025/11/27 10:58
 */
@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 注入自定义用户认证服务（实现了 Spring Security 的 UserDetailsService）
     * 用于根据用户名查询用户信息、密码、权限等
     */
    private final SecurityUserService service;




    /**
     * 核心过滤方法：每个请求执行一次
     *
     * @param request     HTTP 请求
     * @param response    HTTP 响应
     * @param filterChain 过滤器链（必须调用 filterChain.doFilter 让请求继续往下走）
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1. 从请求头获取 Authorization 字段
        String header = request.getHeader("Authorization");

        // 2. 判断 header 是否存在，并且是否符合 Bearer Token 规范
        // 只有符合规范才进入 JWT 解析流程
        if (header != null && header.startsWith("Bearer ")) {
            // 3. 截取真正的 token（去掉 "Bearer " 前缀）
            String token = header.substring(7);

            try {
                // 4. 解析 JWT（第三方 JWT 库解析）
                JWT jwt = JWT.of(token);

                // 5. 从 JWT payload 中提取 userId 字段
                Long id = Long.valueOf(jwt.getPayload("userId").toString());

                // 6. 通过 userId 查询用户详情（包括权限、密码、状态等）
                UserDetails userDetails = service.loadUserByUserId(id);

                // 7. 构建认证对象（Authentication）：
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // 8. 将认证信息存入 Spring Security 的 SecurityContext（线程上下文）
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception ignored) {
                // 9. token 解析失败（非法、篡改、过期等）不直接返回响应，继续放行，让 Spring Security 进入认证/授权阶段触发异常处理器：
            }
        }

        // 10. 继续执行 FilterChain，让请求进入后续流程
        filterChain.doFilter(request, response);
    }
}