package com.lizhengi.admin.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizhengi.system.manager.OldUserManager;
import com.lizhengi.system.pojo.dto.UserCreateDTO;
import com.lizhengi.system.pojo.dto.UserDeleteDTO;
import com.lizhengi.system.pojo.dto.UserListDTO;
import com.lizhengi.system.pojo.dto.UserPageDTO;
import com.lizhengi.system.pojo.dto.UserSelectDTO;
import com.lizhengi.system.pojo.dto.UserUpdateDTO;
import com.lizhengi.system.pojo.entity.UserEntity;
import com.lizhengi.system.pojo.resp.PageInfo;
import com.lizhengi.system.pojo.vo.UserListVO;
import com.lizhengi.system.pojo.vo.UserSelectVO;
import com.lizhengi.system.service.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhengi
 * @date 2025/11/20 10:46
 */
@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {

    private final OldUserManager userManager;

    @Override
    public void createUser(UserCreateDTO dto) {
        UserEntity createEntity = new UserEntity(dto);
        userManager.create(createEntity);
    }

    @Override
    public void deleteUser(UserDeleteDTO dto) {
        userManager.delete(dto.getId());
    }

    @Override
    public void updateUser(UserUpdateDTO dto) {
        UserEntity updateEntity = new UserEntity(dto);
        userManager.updateEntity(updateEntity);
    }

    @Override
    public UserSelectVO selectUser(UserSelectDTO dto) {
        UserEntity entity = userManager.select(dto.getId());
        return new UserSelectVO(entity);
    }

    @Override
    public List<UserListVO> listUser(UserListDTO dto) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StrUtil.isNotEmpty(dto.getId()), UserEntity::getId, dto.getId());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getUsername()), UserEntity::getUsername, dto.getUsername());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getNickname()), UserEntity::getNickname, dto.getNickname());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getRole()), UserEntity::getRole, dto.getRole());

        List<UserEntity> entities = userManager.listByEntity(queryWrapper);
        List<UserListVO> vos = new ArrayList<>();
        for (UserEntity entity : entities) {
            vos.add(new UserListVO(entity));
        }
        return vos;
    }

    @Override
    public PageInfo<UserListVO> pageUser(UserPageDTO dto) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StrUtil.isNotEmpty(dto.getId()), UserEntity::getId, dto.getId());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getUsername()), UserEntity::getUsername, dto.getUsername());
        queryWrapper.like(StrUtil.isNotEmpty(dto.getNickname()), UserEntity::getNickname, dto.getNickname());
        queryWrapper.eq(StrUtil.isNotEmpty(dto.getRole()), UserEntity::getRole, dto.getRole());

        Page<UserEntity> entityPage = userManager.pageQuery(queryWrapper, dto.getPageNum(), dto.getPageSize());

        PageInfo<UserListVO> voPage = new PageInfo<>();
        List<UserListVO> vos = new ArrayList<>();
        for (UserEntity entity : entityPage.getRecords()) {
            vos.add(new UserListVO(entity));
        }
        voPage.setList(vos);
        voPage.setTotal(entityPage.getTotal());
        voPage.setPageSize(dto.getPageSize());
        voPage.setPageNum(dto.getPageNum());

        return voPage;
    }
}
