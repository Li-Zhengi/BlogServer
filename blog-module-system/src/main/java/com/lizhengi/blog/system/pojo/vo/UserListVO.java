package com.lizhengi.blog.system.pojo.vo;


import com.lizhengi.blog.system.pojo.bo.UserBO;
import com.lizhengi.blog.system.pojo.entity.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/20 10:39
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class UserListVO extends UserBO implements Serializable {

    public UserListVO(UserEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
