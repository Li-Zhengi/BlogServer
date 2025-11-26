package com.lizhengi.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 自定义的 AuthenticationEntryPoint 实现类
 *
 * <p>当前端调用受保护接口，但用户尚未登录或登录状态失效时，
 * Spring Security 会触发 AuthenticationEntryPoint。
 * 默认行为通常为跳转到登录页面（HTML），
 * 在前后端分离项目中则需要返回 JSON 告知前端“未登录”。
 *
 * <p>本类功能：
 * 捕获未认证（401）的请求，返回规范的 JSON 提示，
 * 避免前端收到页面重定向。
 *
 * @author lizhengi
 * @date 2025/11/25 16:31
 */
@Component
public class SecurityEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当访问需要认证的接口却未认证时触发此方法
     *
     * @param request 当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param authException 未认证异常，表示登录状态缺失/过期等
     * @throws IOException 写出 JSON 时可能抛出异常
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {

        // 设置响应类型为 JSON，并指定编码
        response.setContentType("application/json;charset=UTF-8");
        // 设置状态码为 401（Unauthorized 未认证）
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 返回统一格式的 JSON 提示
        response.getWriter().write("{\"code\":401,\"msg\":\"未登录，请先登录\"}");
    }
}
