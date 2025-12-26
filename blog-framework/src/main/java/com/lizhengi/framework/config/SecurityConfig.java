package com.lizhengi.framework.config;

import cn.hutool.jwt.JWT;
import com.lizhengi.framework.common.constant.SystemConstant;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

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

    // ========== 1. 基础组件（系统启动时加载）==========

    /**
     * 密码加密器（用于存储密码 & 登录校验），参数：无
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt 自带随机盐
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS 跨域配置源（允许 Vue 前端访问后端接口）
     * 参数说明：无
     * 配置内容：
     * - 允许来源：localhost:5173
     * - 允许 Cookie：true
     * - 允许 Header/Method：*
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * 认证管理器（用于登录接口触发用户认证）
     * 参数：
     *
     * @param configuration 由 Spring 自动提供，内部会装配 UserDetailsService + PasswordEncoder
     * @return AuthenticationManager 认证执行入口
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    // ========== 2. 注入自定义过滤器 & 异常处理器（构建过滤链时使用）==========

    /**
     * JWT 认证失败处理器（未认证时触发 401）
     */
    private final AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 权限不足处理器（已认证但无权限时触发 403）
     */
    private final AccessDeniedHandler accessDeniedHandler;

    /**
     * JWT 解析过滤器（每个请求执行一次），参数：HttpServletRequest / HttpServletResponse / FilterChain（由 OncePerRequestFilter 内部控制）
     */
    private final OncePerRequestFilter jwtAuthenticationFilter;


    // ========== 3. 核心安全过滤链（请求进入后端的执行顺序）==========

    /**
     * Spring Security 过滤链配置
     * 参数：
     *
     * @param http HttpSecurity 负责构建所有安全规则
     * @return SecurityFilterChain 过滤器执行链对象
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ① JWT 过滤器最先执行（在账号密码登录过滤器之前解析身份）
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // ② 关闭 CSRF（前后端分离必须关闭）
                .csrf(AbstractHttpConfigurer::disable)

                // ③ 启用跨域配置（使用上面定义的 CORS Bean）
                .cors(Customizer.withDefaults())

                // ④ 设为无状态，不创建/使用 Session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // ⑤ 统一异常处理入口
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authenticationEntryPoint) // 401
                        .accessDeniedHandler(accessDeniedHandler)           // 403
                )

                // ⑥ 接口权限规则（按路径匹配）
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()    // 登录放行
                        .requestMatchers("/auth/register").permitAll() // 注册放行
                        .requestMatchers("/auth/captcha").permitAll()  // 验证码放行
                        .anyRequest().authenticated()                  // 其他接口必须登录
                )

                // ⑦ 账号密码登录配置（不使用默认登录页，只接受接口 POST 方式）
                .formLogin(form -> form
                        .loginProcessingUrl("/auth/login")      // 处理登录的接口
                        .successHandler(this::loginSuccessHandler) // 登录成功回调
                        .failureHandler(this::loginFailureHandler) // 登录失败回调
                        .permitAll()
                );

        return http.build();
    }


    // ========== 4. 登录回调处理（在认证流程最后触发）==========

    /**
     * 登录成功回调处理（生成 JWT 并返回）
     * 参数：
     *
     * @param request        本次 HTTP 请求对象
     * @param response       HTTP 响应对象（用于写回 JSON）
     * @param authentication 认证成功后的用户信息（含 username / authorities）
     */
    private void loginSuccessHandler(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String username = authentication.getName();

        // 生成 JWT Token
        String token = JWT.create()
                .setPayload("username", username)
                // 1小时过期
                .setExpiresAt(new Date(System.currentTimeMillis() + 3600_000))
                .setKey(SystemConstant.JWT_SIGN_KEY.getBytes(StandardCharsets.UTF_8))
                .sign();

        response.getWriter().write("{\"code\":0,\"token\":\"" + token + "\"}");
    }

    /**
     * 登录失败回调处理（返回统一错误 JSON）
     * 参数：
     *
     * @param request   本次 HTTP 请求对象
     * @param response  HTTP 响应对象（用于写回 JSON）
     * @param exception 认证失败的异常信息（如密码错误、账号不存在）
     */
    private void loginFailureHandler(HttpServletRequest request,
                                     HttpServletResponse response,
                                     AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"msg\":\"用户名或密码错误\"}");
    }
}
