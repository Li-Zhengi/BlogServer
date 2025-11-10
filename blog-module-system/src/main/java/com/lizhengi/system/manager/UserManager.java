package com.lizhengi.system.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhengi.system.mapper.UserMapper;
import com.lizhengi.system.pojo.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/11/7 11:32
 */
@Component
@RequiredArgsConstructor
public class UserManager extends ServiceImpl<UserMapper, UserEntity> {

}
