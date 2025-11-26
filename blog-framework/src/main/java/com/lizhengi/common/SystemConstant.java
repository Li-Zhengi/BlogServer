package com.lizhengi.common;


/**
 * @author lizhengi
 * @date 2025/11/25 16:19
 */
public class SystemConstant {

    /**
     * JWT 签名秘钥（建议改成配置文件读取）
     */
    public static final String JWT_SIGN_KEY = "mySecretKey123!@#";

    /**
     * JWT 有效期（毫秒，默认 24 小时）
     */
    public static final long JWT_EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * HTTP 请求头字段，用来存放 JWT
     */
    public static final String JWT_HEADER = "Authorization";

    /**
     * 前缀，方便前端在 header 里传 token 时区分
     */
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
}
