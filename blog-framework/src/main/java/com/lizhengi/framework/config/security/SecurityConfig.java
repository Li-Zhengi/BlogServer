package com.lizhengi.framework.config.security;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

    // ========== 运行期组件（注入但不实现业务）==========

    /**
     * 用户登陆处理器
     */
    private final CustomAuthResponseHandler responseHandler;

    /**
     * JWT 认证失败处理器（未认证时触发 401, 权限不足时触发 403）
     */
    private final CustomSecurityExceptionHandler exceptionHandler;

    /**
     * JWT 解析过滤器（每个请求执行一次）
     */
    private final CustomJwtAuthenticationFilter authenticationFilter;

    // ========== 核心安全过滤链（请求进入后端的执行顺序）==========

    /**
     * Spring Security 过滤链配置
     * 参数：
     *
     * @param http HttpSecurity 负责构建所有安全规则
     * @return SecurityFilterChain 过滤器执行链对象
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. 先添加 JWT 解析认证过滤器（每个请求执行一次）
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 2. 关闭 CSRF（前后端分离必须关闭）
        http.csrf(AbstractHttpConfigurer::disable);

        // 3. 启用 CORS 跨域配置（会自动使用你定义的 corsConfigurationSource Bean）
        http.cors(Customizer.withDefaults());

        // 4. 设为无状态，不创建或使用 Session
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 5. 统一异常处理（401/403 都交给同一个 handler 处理）
        http.exceptionHandling(handler -> {
            handler.authenticationEntryPoint(exceptionHandler);
            handler.accessDeniedHandler(exceptionHandler);
        });

        // 6. 配置接口访问权限规则
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/auth/login").permitAll();
            auth.requestMatchers("/auth/register").permitAll();
            auth.requestMatchers("/auth/captcha").permitAll();
            auth.anyRequest().authenticated();
        });

        // 7. 账号密码登录接口配置（不使用默认页面，只用 POST 接口）
        http.formLogin(login -> {
            login.loginProcessingUrl("/auth/login");
            // 登陆成功回调
            login.successHandler(responseHandler.loginSuccessHandler());
            // 登陆失败回调
            login.failureHandler(responseHandler.loginFailureHandler());
            login.permitAll();
        });

        return http.build();
    }

    // ========== 启动期基础 Bean ==========

    /**
     * 认证管理器（用于登录接口触发用户认证）
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * CORS 跨域配置源（允许 Vue 前端访问后端接口）
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许来源：localhost:5173
        config.addAllowedOrigin("http://localhost:5173");
        // 允许 Cookie：true
        config.setAllowCredentials(true);
        // 允许 Header/Method：*
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt 自带随机盐
        return new BCryptPasswordEncoder();
    }
}
