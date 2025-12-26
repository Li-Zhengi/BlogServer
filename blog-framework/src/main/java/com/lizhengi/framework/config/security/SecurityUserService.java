package com.lizhengi.framework.config.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author lizhengi
 * @date 2025/12/26 14:18
 */
public interface SecurityUserService extends UserDetailsService {

    Long getUserIdByUsername(String username);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDetails loadUserByUserId(Long username) throws UsernameNotFoundException;
}
