package com.lizhengi.blog.pojo.dto;


import com.lizhengi.blog.pojo.bo.BlogColumnBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lizhengi
 * @date 2025/11/19 10:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogColumnPageDTO extends BlogColumnBO {

    int pageSize;
    int pageNum;

}
