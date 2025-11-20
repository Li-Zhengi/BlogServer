package com.lizhengi.system.service;

import com.lizhengi.system.pojo.dto.UserCreateDTO;
import com.lizhengi.system.pojo.dto.UserDeleteDTO;
import com.lizhengi.system.pojo.dto.UserListDTO;
import com.lizhengi.system.pojo.dto.UserPageDTO;
import com.lizhengi.system.pojo.dto.UserSelectDTO;
import com.lizhengi.system.pojo.dto.UserUpdateDTO;
import com.lizhengi.system.pojo.resp.PageInfo;
import com.lizhengi.system.pojo.vo.UserListVO;
import com.lizhengi.system.pojo.vo.UserSelectVO;

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
