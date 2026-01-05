package com.lizhengi.blog.framework.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



/**
 * BCrypt 工具类
 * 提供密码加密与匹配方法
 *
 * @author lizhengi
 * @date 2025/11/26 15:21
 */
public class BCryptUtil {

    // 可以重用同一个 PasswordEncoder
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 对明文密码进行 BCrypt 加密
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 校验明文密码是否与加密密码匹配
     *
     * @param rawPassword 明文密码
     * @param encodedPassword 数据库中存储的加密密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }

    // 测试
    public static void main(String[] args) {
        String raw = "123456";
        String encoded = encode(raw);
        System.out.println("原始密码: " + raw);
        System.out.println("BCrypt 加密: " + encoded);
        System.out.println("校验结果: " + matches(raw, encoded));
    }
}