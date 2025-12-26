package com.lizhengi.system.pojo.assembler;


import com.lizhengi.framework.common.assembler.BaseAssembler;
import com.lizhengi.framework.common.assembler.BaseMultiAssembler;
import com.lizhengi.framework.common.assembler.SimpleBeanAssembler;
import com.lizhengi.system.pojo.bo.UserBO;
import com.lizhengi.system.pojo.bo.UserCacheBO;
import com.lizhengi.system.pojo.entity.UserEntity;
import com.lizhengi.system.pojo.vo.UserListVO;
import org.springframework.stereotype.Component;

/**
 * @author lizhengi
 * @date 2025/12/22 10:11
 */
@Component
public class UserAssembler extends BaseMultiAssembler<UserEntity, UserBO, UserCacheBO> {

    private final BaseAssembler<UserEntity, UserBO> entityBoAssembler = new SimpleBeanAssembler<>(UserEntity::new, UserBO::new);

    private final BaseAssembler<UserBO, UserListVO> entityListVOAssembler = new SimpleBeanAssembler<>(UserBO::new, UserListVO::new);

    public UserAssembler(BaseAssembler<UserEntity, UserBO> entityBoAssembler, BaseAssembler<UserBO, UserCacheBO> boCacheAssembler) {
        super(entityBoAssembler, boCacheAssembler);
    }

    public UserBO toBO(UserEntity entity) {
        return entityBoAssembler.toTarget(entity);
    }

    public UserEntity toEntity(UserBO bo) {
        return entityBoAssembler.toSource(bo);
    }

    public UserListVO toVO(UserBO bo) {
        return entityListVOAssembler.toTarget(bo);
    }
}
