package com.lizhengi.admin.controller;


import com.lizhengi.blog.pojo.dto.*;
import com.lizhengi.blog.pojo.vo.BlogArticleListVO;
import com.lizhengi.blog.pojo.vo.BlogArticleSelectVO;
import com.lizhengi.blog.pojo.vo.BlogColumnListVO;
import com.lizhengi.blog.pojo.vo.BlogColumnSelectVO;
import com.lizhengi.blog.pojo.vo.BlogDirectionListVO;
import com.lizhengi.blog.pojo.vo.BlogDirectionSelectVO;
import com.lizhengi.blog.pojo.vo.BlogThemeListVO;
import com.lizhengi.blog.pojo.vo.BlogThemeSelectVO;
import com.lizhengi.blog.service.BlogAdminService;
import com.lizhengi.system.pojo.resp.PageInfo;
import com.lizhengi.system.pojo.resp.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lizhengi
 * @date 2025/11/19 11:55
 */
@RestController
@RequestMapping(value = "/blog/admin")
@RequiredArgsConstructor
@Slf4j
public class BlogAdminController {

    private final BlogAdminService blogAdminService;

    // ==========================
    //      活动相关接口
    // ==========================

    // 主题
    @PostMapping("/theme/create")
    public ResponseResult<?> createTheme(@RequestBody BlogThemeCreateDTO dto) {
        blogAdminService.createTheme(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/theme/delete")
    public ResponseResult<?> deleteTheme(@RequestBody BlogThemeDeleteDTO dto) {
        blogAdminService.deleteTheme(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/theme/update")
    public ResponseResult<?> updateTheme(@RequestBody BlogThemeUpdateDTO dto) {
        blogAdminService.updateTheme(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/theme/select")
    public ResponseResult<BlogThemeSelectVO> selectTheme(@RequestBody BlogThemeSelectDTO dto){
        return ResponseResult.buildOkResponse(blogAdminService.selectTheme(dto));
    }

    @PostMapping("/theme/list")
    public ResponseResult<List<BlogThemeListVO>> listTheme(@RequestBody BlogThemeListDTO dto){
        return ResponseResult.buildOkResponse(blogAdminService.listTheme(dto));
    }

    // ==========================
    //      分类相关接口
    // ==========================

    @PostMapping("/direction/create")
    public ResponseResult<?> createDirection(@RequestBody BlogDirectionCreateDTO dto) {
        blogAdminService.createDirection(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/direction/delete")
    public ResponseResult<?> deleteDirection(@RequestBody BlogDirectionDeleteDTO dto) {
        blogAdminService.deleteDirection(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/direction/update")
    public ResponseResult<?> updateDirection(@RequestBody BlogDirectionUpdateDTO dto) {
        blogAdminService.updateDirection(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/direction/select")
    public ResponseResult<BlogDirectionSelectVO> selectDirection(@RequestBody BlogDirectionSelectDTO dto) {
        return ResponseResult.buildOkResponse(blogAdminService.selectDirection(dto));
    }

    @PostMapping("/direction/list")
    public ResponseResult<List<BlogDirectionListVO>> listDirection(@RequestBody BlogDirectionListDTO dto) {
        return ResponseResult.buildOkResponse(blogAdminService.listDirection(dto));
    }

    // ==========================
    //      专栏相关接口
    // ==========================

    @PostMapping("/column/create")
    public ResponseResult<?> createColumn(@RequestBody BlogColumnCreateDTO dto) {
        blogAdminService.createColumn(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/column/delete")
    public ResponseResult<?> deleteColumn(@RequestBody BlogColumnDeleteDTO dto) {
        blogAdminService.deleteColumn(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/column/update")
    public ResponseResult<?> updateColumn(@RequestBody BlogColumnUpdateDTO dto) {
        blogAdminService.updateColumn(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/column/select")
    public ResponseResult<BlogColumnSelectVO> selectColumn(@RequestBody BlogColumnSelectDTO dto) {
        return ResponseResult.buildOkResponse(blogAdminService.selectColumn(dto));
    }

    @PostMapping("/column/list")
    public ResponseResult<List<BlogColumnListVO>> listColumn(@RequestBody BlogColumnListDTO dto) {
        return ResponseResult.buildOkResponse(blogAdminService.listColumn(dto));
    }

    @PostMapping("/column/page")
    public ResponseResult<PageInfo<BlogColumnListVO>> pageColumn(@RequestBody BlogColumnPageDTO dto) {
        return ResponseResult.buildOkResponse(blogAdminService.pageColumn(dto));
    }

    // ==========================
    //      文章相关接口
    // ==========================

    @PostMapping("/article/create")
    public ResponseResult<?> createArticle(@RequestBody BlogArticleCreateDTO dto) {
        blogAdminService.createArticle(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/article/delete")
    public ResponseResult<?> deleteArticle(@RequestBody BlogArticleDeleteDTO dto) {
        blogAdminService.deleteArticle(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/article/update")
    public ResponseResult<?> updateArticle(@RequestBody BlogArticleUpdateDTO dto) {
        blogAdminService.updateArticle(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/article/select")
    public ResponseResult<BlogArticleSelectVO> selectArticle(@RequestBody BlogArticleSelectDTO dto) {
        return ResponseResult.buildOkResponse(blogAdminService.selectArticle(dto));
    }

    @PostMapping("/article/list")
    public ResponseResult<List<BlogArticleListVO>> listArticle(@RequestBody BlogArticleListDTO dto) {
        return ResponseResult.buildOkResponse(blogAdminService.listArticle(dto));
    }

    @PostMapping("/article/page")
    public ResponseResult<PageInfo<BlogArticleListVO>> pageArticle(@RequestBody BlogArticlePageDTO dto) {
        return ResponseResult.buildOkResponse(blogAdminService.pageArticle(dto));
    }
}
