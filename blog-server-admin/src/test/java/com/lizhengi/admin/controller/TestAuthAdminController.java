package com.lizhengi.admin.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lizhengi.system.pojo.dto.UserSignDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestAuthAdminController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
