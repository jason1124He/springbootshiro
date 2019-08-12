package com.sunppenergy.dao;

import com.sunppenergy.entity.UserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunppenergy.vo.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hej
 * @since 2019-07-26
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRoleEntity> {


    /**
     * 获取用户角色信息
     *
     * @param userId
     * @return
     */
    List<UserRoleVO> getUserRoles(@Param("userId") Integer userId);
}
