package com.lizhengi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhengi.system.pojo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author lizhengi
 * @date 2025/11/7 11:09
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
