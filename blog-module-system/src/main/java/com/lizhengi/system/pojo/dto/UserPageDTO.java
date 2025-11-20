package com.lizhengi.system.pojo.dto;


import com.lizhengi.system.pojo.bo.UserBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lizhengi
 * @date 2025/11/20 10:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageDTO extends UserBO implements Serializable {

    int pageSize;
    int pageNum;
}