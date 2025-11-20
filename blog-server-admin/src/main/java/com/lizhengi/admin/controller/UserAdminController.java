package com.lizhengi.admin.controller;


import com.lizhengi.system.pojo.dto.UserCreateDTO;
import com.lizhengi.system.pojo.dto.UserDeleteDTO;
import com.lizhengi.system.pojo.dto.UserListDTO;
import com.lizhengi.system.pojo.dto.UserPageDTO;
import com.lizhengi.system.pojo.dto.UserSelectDTO;
import com.lizhengi.system.pojo.dto.UserUpdateDTO;
import com.lizhengi.system.pojo.resp.PageInfo;
import com.lizhengi.system.pojo.resp.ResponseResult;
import com.lizhengi.system.pojo.vo.UserListVO;
import com.lizhengi.system.pojo.vo.UserSelectVO;
import com.lizhengi.system.service.UserAdminService;
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
@RequestMapping(value = "/user/admin")
@RequiredArgsConstructor
@Slf4j
public class UserAdminController {

    private final UserAdminService userAdminService;

    // ==========================
    //      文章相关接口
    // ==========================

    @PostMapping("/User/create")
    public ResponseResult<?> createUser(@RequestBody UserCreateDTO dto) {
        userAdminService.createUser(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/User/delete")
    public ResponseResult<?> deleteUser(@RequestBody UserDeleteDTO dto) {
        userAdminService.deleteUser(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/User/update")
    public ResponseResult<?> updateUser(@RequestBody UserUpdateDTO dto) {
        userAdminService.updateUser(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/User/select")
    public ResponseResult<UserSelectVO> selectUser(@RequestBody UserSelectDTO dto) {
        return ResponseResult.buildOkResponse(userAdminService.selectUser(dto));
    }

    @PostMapping("/User/list")
    public ResponseResult<List<UserListVO>> listUser(@RequestBody UserListDTO dto) {
        return ResponseResult.buildOkResponse(userAdminService.listUser(dto));
    }

    @PostMapping("/User/page")
    public ResponseResult<PageInfo<UserListVO>> pageUser(@RequestBody UserPageDTO dto) {
        return ResponseResult.buildOkResponse(userAdminService.pageUser(dto));
    }
}
