package com.lizhengi.framework.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;


/**
 * 自定义的 AccessDeniedHandler 实现类
 *
 * <p>当认证通过，但当前用户的权限不足以访问某个资源时，
 * Spring Security 会触发 AccessDeniedHandler。默认情况下，
 * 会跳转到 403 页面，但在前后端分离系统中，我们通常需要返回 JSON。
 *
 * <p>此类的作用就是：
 * 将 403 权限不足的异常以 JSON 格式返回给前端，
 * 避免前端收到 HTML 页面导致解析错误。
 *
 * @author lizhengi
 * @date 2025/11/25 16:32
 */
@Component
public class SecurityDeniedHandler implements AccessDeniedHandler {

    /**
     * 处理权限不足（403）异常
     *
     * @param request 当前 HTTP 请求对象
     * @param response 当前 HTTP 响应对象
     * @param accessDeniedException 权限不足异常对象
     * @throws IOException 写出 JSON 结果时可能抛出的异常
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        // 设置响应类型为 JSON，且编码为 UTF-8
        response.setContentType("application/json;charset=UTF-8");
        // 设置 HTTP 状态码为 403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // 返回简单的 JSON 信息（也可封装为统一返回格式）
        response.getWriter().write("{\"code\":403,\"msg\":\"权限不足\"}");
    }
}