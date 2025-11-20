package com.lizhengi.system.pojo.vo;


import com.lizhengi.system.pojo.bo.UserBO;
import com.lizhengi.system.pojo.entity.UserEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/20 10:39
 */
public class UserListVO extends UserBO implements Serializable {

    public UserListVO(UserEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
