package com.lizhengi.system.pojo.resp;


import lombok.Data;

import java.util.List;

/**
 * @author lizhengi
 * @date 2025/11/19 10:56
 */
@Data
public class PageInfo<V> {

    private long pageNum;

    private int pageSize;

    private List<V> list;

    private long total;
}
