package com.lizhengi.blog.blog.pojo.dto;


import com.lizhengi.blog.blog.pojo.bo.BlogArticleBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lizhengi
 * @date 2025/11/19 10:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogArticlePageDTO extends BlogArticleBO {

    int pageSize;
    int pageNum;
}

