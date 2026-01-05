package com.lizhengi.blog.blog.pojo.bo;


import lombok.Data;

/**
 * @author lizhengi
 * @date 2025/11/10 10:19
 */
@Data
public class BlogColumnBO {

    private Long id;

    private String themeId;

    private String directionId;

    private String name;

    private String coverImg;

    private String description;
}
