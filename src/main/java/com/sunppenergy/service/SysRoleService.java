package com.sunppenergy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sunppenergy.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunppenergy.vo.SysRoleVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 新建角色并分配权限
     *
     * @param sysRoleVO
     * @return
     */
    Boolean saveRole(SysRoleVO sysRoleVO);

    /**
     * 删除角色
     *
     * @param ids
     * @param realDelete
     * @return
     */
    Boolean deleteRoleByIds(List<Integer> ids, Integer realDelete);


    /**
     * 更新角色信息
     *
     * @param sysRoleVO
     * @return
     */
    Boolean updateRoleById(SysRoleVO sysRoleVO);

    /**
     * 根据条件获取角色分页信息
     *
     * @param pageIndex
     * @param pageSize
     * @param name
     * @return
     */
    IPage<SysRoleVO> getRoles(Integer pageIndex, Integer pageSize, String name);

    /**
     * 根据条件获取角色信息
     *
     * @param name
     * @return
     */
    List<SysRoleVO> getAllRoles(String name);
}
