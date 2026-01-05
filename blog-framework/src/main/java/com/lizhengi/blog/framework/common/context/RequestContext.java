package com.lizhengi.blog.framework.common.context;



import lombok.Builder;

import java.util.Locale;

/**
 * @author lizhengi
 * @date 2025/12/23 15:17
 */
@Builder
public final class RequestContext {

    /** 当前请求唯一标识 */
    private final String requestId;

    /** 当前操作者ID（不是 User 对象） */
    private final String userId;

    /** 租户、语言等 */
    private final String tenantId;
    private final Locale locale;


}
