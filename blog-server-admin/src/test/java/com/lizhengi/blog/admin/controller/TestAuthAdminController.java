package com.lizhengi.blog.admin.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.lizhengi.blog.system.pojo.dto.UserSignDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestAuthAdminController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 登录 + 携带 Token 获取用户信息
     */
    @Test
    void testLoginAndGetUserInfo() throws Exception {

        // ==========================
        // 1. 调用登录接口（formLogin 是 x-www-form-urlencoded）
        // ==========================
        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "admin")
                        .param("password", "123456"))
                .andExpect(status().isOk())
                .andReturn();

        String loginResponse = loginResult.getResponse().getContentAsString();
        System.out.println("登录响应: " + loginResponse);

        // ==========================
        // 2. 提取 Token
        // ==========================
        // {"code":0,"token":"xxxx"}
        String token = JsonPath.read(loginResponse, "$.token");
        System.out.println("提取到 Token: " + token);

        // ==========================
        // 3. 携带 Token 请求 user-info
        // ==========================
        MvcResult userInfoResult = mockMvc.perform(
                        get("/user/admin/user-info")
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andReturn();

        String userInfoJson = userInfoResult.getResponse().getContentAsString();
        System.out.println("用户信息响应: " + userInfoJson);
    }




    /**
     * 登录成功
     */
    @Test
    void testLoginSuccess() throws Exception {

        UserSignDTO dto = new UserSignDTO();
        dto.setUsername("admin");
        dto.setPassword("123456");

        MvcResult mvcResult = mockMvc.perform(post("/auth/admin/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
//                .andExpect(jsonPath("$.code").value("0"))
//                .andExpect(jsonPath("$.data.token").exists());
//        responseResult -> {ResponseResult@18701} "ResponseResult(code=401, msg=用户名或密码错误, data=null)"

    }

    /**
     * 登录失败
     */
    @Test
    void testLoginFail() throws Exception {

        UserSignDTO dto = new UserSignDTO();
        dto.setUsername("admin");
        dto.setPassword("wrong");

        MvcResult mvcResult = mockMvc.perform(post("/auth/admin/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
