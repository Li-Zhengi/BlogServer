package com.lizhengi.blog.blog.service;

import com.lizhengi.blog.blog.pojo.dto.*;
import com.lizhengi.blog.blog.pojo.vo.BlogArticleListVO;
import com.lizhengi.blog.blog.pojo.vo.BlogArticleSelectVO;
import com.lizhengi.blog.blog.pojo.vo.BlogColumnListVO;
import com.lizhengi.blog.blog.pojo.vo.BlogColumnSelectVO;
import com.lizhengi.blog.blog.pojo.vo.BlogDirectionListVO;
import com.lizhengi.blog.blog.pojo.vo.BlogDirectionSelectVO;
import com.lizhengi.blog.blog.pojo.vo.BlogThemeListVO;
import com.lizhengi.blog.blog.pojo.vo.BlogThemeSelectVO;
import com.lizhengi.blog.common.pojo.resp.PageInfo;

import java.util.List;

/**
 * @author lizhengi
 * @date 2025/11/13 15:10
 */
public interface BlogAdminService {

    // 主题

    void createTheme(BlogThemeCreateDTO dto);

    void deleteTheme(BlogThemeDeleteDTO dto);

    void updateTheme(BlogThemeUpdateDTO dto);

    BlogThemeSelectVO selectTheme(BlogThemeSelectDTO dto);

    List<BlogThemeListVO> listTheme(BlogThemeListDTO dto);

    // 分类

    void createDirection(BlogDirectionCreateDTO dto);

    void deleteDirection(BlogDirectionDeleteDTO dto);

    void updateDirection(BlogDirectionUpdateDTO dto);

    BlogDirectionSelectVO selectDirection(BlogDirectionSelectDTO dto);

    List<BlogDirectionListVO> listDirection(BlogDirectionListDTO dto);

    // 专栏

    void createColumn(BlogColumnCreateDTO dto);

    void deleteColumn(BlogColumnDeleteDTO dto);

    void updateColumn(BlogColumnUpdateDTO dto);

    BlogColumnSelectVO selectColumn(BlogColumnSelectDTO dto);

    List<BlogColumnListVO> listColumn(BlogColumnListDTO dto);

    PageInfo<BlogColumnListVO> pageColumn(BlogColumnPageDTO dto);

    // 文章

    void createArticle(BlogArticleCreateDTO dto);

    void deleteArticle(BlogArticleDeleteDTO dto);

    void updateArticle(BlogArticleUpdateDTO dto);

    BlogArticleSelectVO selectArticle(BlogArticleSelectDTO dto);

    List<BlogArticleListVO> listArticle(BlogArticleListDTO dto);

    PageInfo<BlogArticleListVO> pageArticle(BlogArticlePageDTO dto);

}
