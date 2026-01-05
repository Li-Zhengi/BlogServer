package com.lizhengi.blog.system.pojo.assembler;


import com.lizhengi.blog.framework.common.assembler.BaseAssembler;
import com.lizhengi.blog.framework.common.assembler.BaseMultiAssembler;
import com.lizhengi.blog.framework.common.assembler.SimpleBeanAssembler;
import com.lizhengi.blog.system.pojo.bo.UserBO;
import com.lizhengi.blog.system.pojo.bo.UserCacheBO;
import com.lizhengi.blog.system.pojo.entity.UserEntity;
import com.lizhengi.blog.system.pojo.vo.UserListVO;

/**
 * @author lizhengi
 * @date 2025/12/22 10:11
 */
//@Component
public class UserAssembler extends BaseMultiAssembler<UserEntity, UserBO, UserCacheBO> {

    private final BaseAssembler<UserEntity, UserBO> entityBoAssembler = new SimpleBeanAssembler<>(UserEntity::new, UserBO::new);

    private final BaseAssembler<UserBO, UserListVO> entityListVOAssembler = new SimpleBeanAssembler<>(UserBO::new, UserListVO::new);

    public UserAssembler() {
        super(new SimpleBeanAssembler<>(UserEntity::new, UserBO::new), new SimpleBeanAssembler<>(UserBO::new, UserCacheBO::new));
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
