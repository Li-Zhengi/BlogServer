package com.lizhengi.config;

import cn.hutool.jwt.JWT;
import com.lizhengi.common.SystemConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Spring Security 核心安全配置类
 *
 * <p>本配置主要实现：
 * 1. 自定义 AuthenticationEntryPoint（未登录处理）
 * 2. 自定义 AccessDeniedHandler（权限不足处理）
 * 3. 配置密码加密方案（BCrypt）
 * 4. 注册 AuthenticationManager，用于执行用户名/密码认证
 * 5. 配置 HttpSecurity，包括 csrf、cors、会话策略、接口权限
 * 6. 配置表单登录 loginProcessingUrl、登录成功/失败处理
 *
 * <p>适用场景：
 * - 前后端分离系统
 * - 使用 Token（如 JWT）进行无状态认证
 * - 登录接口返回 JSON，而非跳转页面
 *
 * <p>注意：
 * - Spring Security 6.x / Spring Boot 3.x 不再推荐继承 WebSecurityConfigurerAdapter
 * - 所有配置均基于新的 DSL 写法
 *
 * @author lizhengi
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // ========================
    //   注入自定义组件
    // ========================

    /**
     * 未认证（401）异常处理
     * <p>
     * 用户访问需要登录的接口但未认证时触发，
     * 例如 JWT 缺失、过期，无身份信息等情况。
     */
    private final AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 权限不足（403）异常处理
     * <p>
     * 用户已登录，但访问权限不足时触发，
     * 例如普通用户访问管理员接口。
     */
    private final AccessDeniedHandler accessDeniedHandler;

    // ========================
    //   密码加密器
    // ========================

    /**
     * 密码加密器：
     * <p>
     * 使用 BCrypt 算法进行密码加密，内部带随机盐值。
     * Spring Security 在认证时会自动进行匹配：
     * rawPassword -> BCrypt -> encodedPassword
     *
     * @return BCryptPasswordEncoder 实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ========================
    //   AuthenticationManager
    // ========================

    /**
     * AuthenticationManager
     * <p>
     * 最新推荐写法：让 Spring 自动组装 AuthenticationManager，
     * 会自动发现 UserDetailsService + PasswordEncoder 并创建 DaoAuthenticationProvider。
     *
     * @param configuration Spring 提供的认证配置
     * @return AuthenticationManager 实例
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // ========================
    //   HttpSecurity 核心安全规则
    // ========================

    /**
     * 配置安全过滤链
     * <p>
     * 包含：
     * - CSRF 关闭（前后端分离系统必须关闭）
     * - CORS 开启（允许跨域请求）
     * - 会话策略：STATELESS（完全依赖 JWT，无 session）
     * - 自定义异常处理（401 / 403）
     * - 接口权限控制
     * - 表单登录（loginProcessingUrl、成功/失败处理）
     *
     * @param http HttpSecurity 对象
     * @return SecurityFilterChain 实例
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 禁用 CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // 开启 CORS
                .cors(Customizer.withDefaults())

                // 会话策略：STATELESS
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 异常处理
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                // 接口权限
                .authorizeHttpRequests(auth -> auth
                        // 注册接口放行
                        .requestMatchers("/auth/admin/register").permitAll()
                        // 验证码接口放行
                        .requestMatchers("/auth/admin/captcha").permitAll()
                        // 其他接口必须登录
                        .anyRequest().authenticated()
                )

                // 表单登录
                .formLogin(form -> form
                        // 登录入口
                        .loginProcessingUrl("/auth/login")
                        // 登录成功处理
                        .successHandler(this::loginSuccessHandler)
                        // 登录失败处理
                        .failureHandler(this::loginFailureHandler)
                        .permitAll()
                );

        return http.build();
    }

    // ========================
    //   登录处理逻辑
    // ========================

    /**
     * 登录成功处理
     * <p>
     * 可在此生成 JWT 并返回给前端
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param authentication 认证信息
     * @throws IOException 异常
     */
    private void loginSuccessHandler(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String username = authentication.getName();
        String token = JWT.create()
                .setPayload("username", username)
                .setExpiresAt(new Date(System.currentTimeMillis() + 3600_000))
                .setKey(SystemConstant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8))
                .sign();
        response.getWriter().write("{\"code\":0,\"token\":\"" + token + "\"}");
    }

    /**
     * 登录失败处理
     * <p>
     * 用户名或密码错误时返回统一 JSON 格式
     *
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param exception AuthenticationException
     * @throws IOException 异常
     */
    private void loginFailureHandler(HttpServletRequest request,
                                     HttpServletResponse response,
                                     AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"msg\":\"用户名或密码错误\"}");
    }

}