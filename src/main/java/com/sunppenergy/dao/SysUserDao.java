package com.sunppenergy.dao;

import com.sunppenergy.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {


    /**
     * 查找用户角色权限信息
     * @param userId
     * @return
     */
    SysUserEntity findUserById(@Param("userId") Integer userId);
}
