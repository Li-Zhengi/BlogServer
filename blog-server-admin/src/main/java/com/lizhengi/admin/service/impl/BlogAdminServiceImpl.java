package com.lizhengi.admin.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizhengi.blog.pojo.dto.*;
import com.lizhengi.blog.pojo.entity.BlogArticleEntity;
import com.lizhengi.blog.pojo.entity.BlogColumnEntity;
import com.lizhengi.blog.pojo.entity.BlogDirectionEntity;
import com.lizhengi.blog.pojo.entity.BlogThemeEntity;
import com.lizhengi.blog.pojo.vo.BlogArticleListVO;
import com.lizhengi.blog.pojo.vo.BlogArticleSelectVO;
import com.lizhengi.blog.pojo.vo.BlogColumnListVO;
import com.lizhengi.blog.pojo.vo.BlogColumnSelectVO;
import com.lizhengi.blog.pojo.vo.BlogDirectionListVO;
import com.lizhengi.blog.pojo.vo.BlogDirectionSelectVO;
import com.lizhengi.blog.pojo.vo.BlogThemeListVO;
import com.lizhengi.blog.pojo.vo.BlogThemeSelectVO;
import com.lizhengi.blog.service.BlogAdminService;
import com.lizhengi.blog.manager.BlogArticleManager;
import com.lizhengi.blog.manager.BlogColumnManager;
import com.lizhengi.blog.manager.BlogDirectionManager;
import com.lizhengi.blog.manager.BlogThemeManager;
import com.lizhengi.system.pojo.resp.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhengi
 * @date 2025/11/13 15:10
 */
@Service
@RequiredArgsConstructor
public class BlogAdminServiceImpl implements BlogAdminService {

    private final BlogThemeManager blogThemeManager;

    private final BlogDirectionManager blogDirectionManager;

    private final BlogColumnManager blogColumnManager;

    private final BlogArticleManager blogArticleManager;

    @Override
    public void createTheme(BlogThemeCreateDTO dto) {
        BlogThemeEntity createEntity = new BlogThemeEntity(dto);
        blogThemeManager.create(createEntity);
    }

    @Override
    public void deleteTheme(BlogThemeDeleteDTO dto) {
        blogThemeManager.delete(dto.getId());
    }

    @Override
    public void updateTheme(BlogThemeUpdateDTO dto) {
        BlogThemeEntity updateEntity = new BlogThemeEntity(dto);
        blogThemeManager.updateEntity(updateEntity);
    }

    @Override
    public BlogThemeSelectVO selectTheme(BlogThemeSelectDTO dto) {
        BlogThemeEntity entity = blogThemeManager.select(dto.getId());
        return new BlogThemeSelectVO(entity);
    }

    @Override
    public List<BlogThemeListVO> listTheme(BlogThemeListDTO dto) {

        LambdaQueryWrapper<BlogThemeEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StrUtil.isNotEmpty(dto.getId()), BlogThemeEntity::getId, dto.getId());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getName()), BlogThemeEntity::getName, dto.getName());

        List<BlogThemeEntity> entities = blogThemeManager.listByEntity(queryWrapper);
        List<BlogThemeListVO> vos = new ArrayList<>();
        for (BlogThemeEntity entity : entities) {
            vos.add(new BlogThemeListVO(entity));
        }
        return vos;
    }


    @Override
    public void createDirection(BlogDirectionCreateDTO dto) {
        BlogDirectionEntity createEntity = new BlogDirectionEntity(dto);
        blogDirectionManager.create(createEntity);
    }

    @Override
    public void deleteDirection(BlogDirectionDeleteDTO dto) {
        blogDirectionManager.delete(dto.getId());
    }

    @Override
    public void updateDirection(BlogDirectionUpdateDTO dto) {
        BlogDirectionEntity updateEntity = new BlogDirectionEntity(dto);
        blogDirectionManager.updateEntity(updateEntity);
    }

    @Override
    public BlogDirectionSelectVO selectDirection(BlogDirectionSelectDTO dto) {
        BlogDirectionEntity entity = blogDirectionManager.select(dto.getId());
        return new BlogDirectionSelectVO(entity);
    }

    @Override
    public List<BlogDirectionListVO> listDirection(BlogDirectionListDTO dto) {
        LambdaQueryWrapper<BlogDirectionEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StrUtil.isNotEmpty(dto.getId()), BlogDirectionEntity::getId, dto.getId());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getName()), BlogDirectionEntity::getName, dto.getName());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getThemeId()), BlogDirectionEntity::getThemeId, dto.getThemeId());

        List<BlogDirectionEntity> entities = blogDirectionManager.listByEntity(queryWrapper);
        List<BlogDirectionListVO> vos = new ArrayList<>();
        for (BlogDirectionEntity entity : entities) {
            vos.add(new BlogDirectionListVO(entity));
        }
        return vos;
    }


    @Override
    public void createColumn(BlogColumnCreateDTO dto) {
        BlogColumnEntity createEntity = new BlogColumnEntity(dto);
        blogColumnManager.create(createEntity);
    }

    @Override
    public void deleteColumn(BlogColumnDeleteDTO dto) {
        blogColumnManager.delete(dto.getId());
    }

    @Override
    public void updateColumn(BlogColumnUpdateDTO dto) {
        BlogColumnEntity updateEntity = new BlogColumnEntity(dto);
        blogColumnManager.updateEntity(updateEntity);
    }

    @Override
    public BlogColumnSelectVO selectColumn(BlogColumnSelectDTO dto) {
        BlogColumnEntity entity = blogColumnManager.select(dto.getId());
        return new BlogColumnSelectVO(entity);
    }

    @Override
    public List<BlogColumnListVO> listColumn(BlogColumnListDTO dto) {
        LambdaQueryWrapper<BlogColumnEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StrUtil.isNotEmpty(dto.getId()), BlogColumnEntity::getId, dto.getId());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getName()), BlogColumnEntity::getName, dto.getName());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getThemeId()), BlogColumnEntity::getThemeId, dto.getThemeId());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getDirectionId()), BlogColumnEntity::getDirectionId, dto.getDirectionId());

        List<BlogColumnEntity> entities = blogColumnManager.listByEntity(queryWrapper);
        List<BlogColumnListVO> vos = new ArrayList<>();
        for (BlogColumnEntity entity : entities) {
            vos.add(new BlogColumnListVO(entity));
        }
        return vos;
    }

    @Override
    public PageInfo<BlogColumnListVO> pageColumn(BlogColumnPageDTO dto) {

        LambdaQueryWrapper<BlogColumnEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StrUtil.isNotEmpty(dto.getId()), BlogColumnEntity::getId, dto.getId());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getName()), BlogColumnEntity::getName, dto.getName());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getThemeId()), BlogColumnEntity::getThemeId, dto.getThemeId());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getDirectionId()), BlogColumnEntity::getDirectionId, dto.getDirectionId());

        Page<BlogColumnEntity> entityPage = blogColumnManager.pageQuery(queryWrapper, dto.getPageNum(), dto.getPageSize());

        PageInfo<BlogColumnListVO> voPage = new PageInfo<>();
        List<BlogColumnListVO> vos = new ArrayList<>();
        for (BlogColumnEntity entity : entityPage.getRecords()) {
            vos.add(new BlogColumnListVO(entity));
        }
        voPage.setQueryResults(vos);
        voPage.setTotal(entityPage.getTotal());
        voPage.setPageSize(dto.getPageSize());
        voPage.setPageNum(dto.getPageNum());

        return voPage;
    }


    @Override
    public void createArticle(BlogArticleCreateDTO dto) {
        BlogArticleEntity createEntity = new BlogArticleEntity(dto);
        blogArticleManager.create(createEntity);
    }

    @Override
    public void deleteArticle(BlogArticleDeleteDTO dto) {
        blogArticleManager.delete(dto.getId());
    }

    @Override
    public void updateArticle(BlogArticleUpdateDTO dto) {
        BlogArticleEntity updateEntity = new BlogArticleEntity(dto);
        blogArticleManager.updateEntity(updateEntity);
    }

    @Override
    public BlogArticleSelectVO selectArticle(BlogArticleSelectDTO dto) {
        BlogArticleEntity entity = blogArticleManager.select(dto.getId());
        return new BlogArticleSelectVO(entity);
    }

    @Override
    public List<BlogArticleListVO> listArticle(BlogArticleListDTO dto) {
        LambdaQueryWrapper<BlogArticleEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StrUtil.isNotEmpty(dto.getId()), BlogArticleEntity::getId, dto.getId());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getName()), BlogArticleEntity::getName, dto.getName());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getColumnId()), BlogArticleEntity::getColumnId, dto.getColumnId());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getAuthorId()), BlogArticleEntity::getAuthorId, dto.getAuthorId());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getStatus()), BlogArticleEntity::getStatus, dto.getStatus());

        List<BlogArticleEntity> entities = blogArticleManager.listByEntity(queryWrapper);
        List<BlogArticleListVO> vos = new ArrayList<>();
        for (BlogArticleEntity entity : entities) {
            vos.add(new BlogArticleListVO(entity));
        }
        return vos;
    }

    @Override
    public PageInfo<BlogArticleListVO> pageArticle(BlogArticlePageDTO dto) {
        LambdaQueryWrapper<BlogArticleEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StrUtil.isNotEmpty(dto.getId()), BlogArticleEntity::getId, dto.getId());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getName()), BlogArticleEntity::getName, dto.getName());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getColumnId()), BlogArticleEntity::getColumnId, dto.getColumnId());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getAuthorId()), BlogArticleEntity::getAuthorId, dto.getAuthorId());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getStatus()), BlogArticleEntity::getStatus, dto.getStatus());

        Page<BlogArticleEntity> entityPage = blogArticleManager.pageQuery(queryWrapper, dto.getPageNum(), dto.getPageSize());

        PageInfo<BlogArticleListVO> voPage = new PageInfo<>();
        List<BlogArticleListVO> vos = new ArrayList<>();
        for (BlogArticleEntity entity : entityPage.getRecords()) {
            vos.add(new BlogArticleListVO(entity));
        }
        voPage.setQueryResults(vos);
        voPage.setTotal(entityPage.getTotal());
        voPage.setPageSize(dto.getPageSize());
        voPage.setPageNum(dto.getPageNum());

        return voPage;
    }
}
