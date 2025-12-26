package com.lizhengi.framework.config.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lizhengi
 * @date 2025/12/26 14:47
 */
@Component
public class CustomSecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    /**
     * 处理未认证（401）异常
     *
     * @param request 当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param authException 未认证异常对象
     * @throws IOException 写出 JSON 时可能抛出异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        // 设置响应类型为 JSON，并指定编码
        response.setContentType("application/json;charset=UTF-8");
        // 设置状态码为 401（Unauthorized 未认证）
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 返回统一格式的 JSON 提示
        response.getWriter().write("{\"code\":401,\"msg\":\"未登录，请先登录\"}");
    }

    /**
     * 处理权限不足（403）异常
     *
     * @param request 当前 HTTP 请求对象
     * @param response 当前 HTTP 响应对象
     * @param accessDeniedException 权限不足异常对象
     * @throws IOException 写出 JSON 结果时可能抛出的异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {

        // 设置响应类型为 JSON，且编码为 UTF-8
        response.setContentType("application/json;charset=UTF-8");
        // 设置 HTTP 状态码为 403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // 返回简单的 JSON 信息（也可封装为统一返回格式）
        response.getWriter().write("{\"code\":403,\"msg\":\"权限不足\"}");
    }
}
