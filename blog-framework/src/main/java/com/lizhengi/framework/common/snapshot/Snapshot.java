package com.lizhengi.framework.common.snapshot;


import java.time.Instant;

/**
 * @author lizhengi
 * @date 2025/12/23 14:56
 */
public interface Snapshot {

    /** 快照发生时间 */
    Instant occurredAt();

    /** 快照类型 / 原因 */
    String type();

    /** 幂等 key */
    String idempotentKey();
}
