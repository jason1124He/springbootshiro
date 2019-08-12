package com.sunppenergy.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunppenergy.entity.RoleAuthEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunppenergy.vo.RoleAuthVO;
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
public interface RoleAuthDao extends BaseMapper<RoleAuthEntity> {

    List<RoleAuthVO> getRoleAndAuths(@Param("roleId") Integer roleId);
}
