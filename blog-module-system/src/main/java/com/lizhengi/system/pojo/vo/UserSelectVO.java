package com.lizhengi.system.pojo.vo;


import com.lizhengi.system.pojo.bo.UserBO;
import com.lizhengi.system.pojo.entity.UserEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/20 10:38
 */
public class UserSelectVO extends UserBO implements Serializable {

    public UserSelectVO(UserEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }

}
