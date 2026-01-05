package com.lizhengi.blog.admin.controller;


import cn.hutool.jwt.JWT;
import com.lizhengi.blog.system.pojo.dto.UserCreateDTO;
import com.lizhengi.blog.system.pojo.dto.UserDeleteDTO;
import com.lizhengi.blog.system.pojo.dto.UserListDTO;
import com.lizhengi.blog.system.pojo.dto.UserPageDTO;
import com.lizhengi.blog.system.pojo.dto.UserSelectDTO;
import com.lizhengi.blog.system.pojo.dto.UserUpdateDTO;
import com.lizhengi.blog.common.pojo.resp.PageInfo;
import com.lizhengi.blog.common.pojo.resp.ResponseResult;
import com.lizhengi.blog.system.pojo.vo.UserListVO;
import com.lizhengi.blog.system.pojo.vo.UserSelectVO;
import com.lizhengi.blog.system.service.UserAdminService;
import com.lizhengi.blog.system.service.impl.UserAuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final UserAuthServiceImpl userDetailsService;

    /**
     * 解析 Token 并返回用户信息
     */
    @GetMapping("/user-info")
    public ResponseResult<?> getUserInfo(HttpServletRequest request) {

        // 1. 从 Header 取 Token
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseResult.buildFailResponse("401", "未携带 Token");
        }

        String token = header.substring(7);

        try {
            // 2. 解析 Token
            JWT jwt = JWT.of(token);
            String username = jwt.getPayload("username").toString();

            // 3. 查询用户信息（使用你写的 selectUserByUsername）
            UserSelectVO user = userDetailsService.selectUserByUsername(username);

            // 4. 返回
            return ResponseResult.buildOkResponse(user);

        } catch (Exception e) {
            log.error("Token 解析失败", e);
            return ResponseResult.buildFailResponse("401", "Token 无效或已过期");
        }
    }

    // ==========================
    //      文章相关接口
    // ==========================

    @PostMapping("/user/create")
    public ResponseResult<?> createUser(@RequestBody UserCreateDTO dto) {
        userAdminService.createUser(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/user/delete")
    public ResponseResult<?> deleteUser(@RequestBody UserDeleteDTO dto) {
        userAdminService.deleteUser(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/user/update")
    public ResponseResult<?> updateUser(@RequestBody UserUpdateDTO dto) {
        userAdminService.updateUser(dto);
        return ResponseResult.buildOkResponse();
    }

    @PostMapping("/user/select")
    public ResponseResult<UserSelectVO> selectUser(@RequestBody UserSelectDTO dto) {
        return ResponseResult.buildOkResponse(userAdminService.selectUser(dto));
    }

    @PostMapping("/user-list")
    public ResponseResult<List<UserListVO>> listUser(@RequestBody UserListDTO dto) {
        return ResponseResult.buildOkResponse(userAdminService.listUser(dto));
    }

    @PostMapping("/user-page")
    public ResponseResult<PageInfo<UserListVO>> pageUser(@RequestBody UserPageDTO dto) {
        return ResponseResult.buildOkResponse(userAdminService.pageUser(dto));
    }
}
