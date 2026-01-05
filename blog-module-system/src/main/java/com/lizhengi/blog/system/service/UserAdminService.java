package com.lizhengi.blog.system.service;

import com.lizhengi.blog.system.pojo.dto.UserCreateDTO;
import com.lizhengi.blog.system.pojo.dto.UserDeleteDTO;
import com.lizhengi.blog.system.pojo.dto.UserListDTO;
import com.lizhengi.blog.system.pojo.dto.UserPageDTO;
import com.lizhengi.blog.system.pojo.dto.UserSelectDTO;
import com.lizhengi.blog.system.pojo.dto.UserUpdateDTO;
import com.lizhengi.blog.common.pojo.resp.PageInfo;
import com.lizhengi.blog.system.pojo.vo.UserListVO;
import com.lizhengi.blog.system.pojo.vo.UserSelectVO;

import java.util.List;

/**
 * @author lizhengi
 * @date 2025/11/20 10:00
 */
public interface UserAdminService {


    // 角色

    void createUser(UserCreateDTO dto);

    void deleteUser(UserDeleteDTO dto);

    void updateUser(UserUpdateDTO dto);

    UserSelectVO selectUser(UserSelectDTO dto);

    List<UserListVO> listUser(UserListDTO dto);

    PageInfo<UserListVO> pageUser(UserPageDTO dto);

}
