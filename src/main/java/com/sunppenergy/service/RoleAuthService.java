package com.sunppenergy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunppenergy.entity.RoleAuthEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunppenergy.vo.RoleAuthVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hej
 * @since 2019-07-26
 */
public interface RoleAuthService extends IService<RoleAuthEntity> {

    /**
     * 获取角色的权限信息
     *
     * @param roleId
     * @return
     */
    List<RoleAuthVO> getRoleAndAuths(Integer roleId);
}
