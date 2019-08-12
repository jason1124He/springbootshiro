package com.sunppenergy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunppenergy.entity.UserRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunppenergy.vo.UserRoleVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hej
 * @since 2019-07-26
 */
public interface UserRoleService extends IService<UserRoleEntity> {


    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    List<UserRoleVO> getUserRoles(Integer userId);
}
