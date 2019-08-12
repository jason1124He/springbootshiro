package com.sunppenergy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sunppenergy.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunppenergy.vo.SysRoleVO;
import com.sunppenergy.vo.SysUserVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 通过用户名查找用户信息（包含权限角色信息）
     */
    SysUserEntity findByName(String userName);

    /**
     * 保存用户以及用户的角色信息
     *
     * @param userVO
     * @return
     */
    SysUserEntity saveUserAndRole(SysUserVO userVO);

    /**
     * 通过用户名查找用户信息
     *
     * @param userName
     * @return
     */
    Boolean findUserByUserName(String userName);

    /**
     * 修改有回信息
     *
     * @param userVo
     * @return
     */
    Boolean updateUser(SysUserVO userVo);

    /**
     * 删除用户信息，物理删除
     *
     * @param userId
     * @param realDelete 0:逻辑删除1：物理删除
     * @return
     */
    Boolean deleteUser(Integer userId, int realDelete);

    /**
     * 根据条件获取用户分页信息
     *
     * @param pageIndex
     * @param pageSize
     * @param userName
     * @param realName
     * @param substationId
     * @param phone
     * @return
     */
    IPage<SysUserVO> getUsers(Integer pageIndex, Integer pageSize, String userName, String realName, Integer substationId, String phone);
}
