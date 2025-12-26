package com.lizhengi.framework.config.security;


import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author lizhengi
 * @date 2025/12/26 14:18
 */
public interface SecurityUserService extends UserDetailsService {

    Long getUserIdByUsername(String username);
}
