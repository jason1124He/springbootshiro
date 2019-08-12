package com.sunppenergy.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunppenergy.common.ShiroUtils;
import com.sunppenergy.entity.RoleAuthEntity;
import com.sunppenergy.entity.SysRoleEntity;
import com.sunppenergy.entity.SysUserEntity;
import com.sunppenergy.dao.SysUserDao;
import com.sunppenergy.entity.UserRoleEntity;
import com.sunppenergy.service.SysRoleService;
import com.sunppenergy.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunppenergy.service.UserRoleService;
import com.sunppenergy.utils.PasswordUtill;
import com.sunppenergy.utils.StringUtils;
import com.sunppenergy.vo.SysRoleVO;
import com.sunppenergy.vo.SysUserVO;
import com.sunppenergy.vo.UserRoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 通过用户名查找用户信息（包含权限角色信息）
     *
     * @param userName 用户名
     * @return
     */
    @Override
    public SysUserEntity findByName(String userName) {

        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserEntity::getUserName, userName);
        SysUserEntity user = getOne(queryWrapper);
        if (null != user) {
            user = sysUserDao.findUserById(user.getId());
        }
        return user;
    }

    /**
     * 保存用户以及其角色信息
     *
     * @param userVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserEntity saveUserAndRole(SysUserVO userVO) {

        Set<SysRoleEntity> roleEntityList = new HashSet<>();

        SysUserEntity user = new SysUserEntity();
        BeanUtils.copyProperties(userVO, user);
        SysUserEntity currentUser = ShiroUtils.getCurrentUser();

        user.setCreateUser(null == currentUser ? 0 : currentUser.getId());
        String password = PasswordUtill.encrypt(user.getUserName(), user.getPassword());
        user.setPassword(password);
        boolean saveFlag = save(user);

        if (saveFlag) {
            Integer userId = user.getId();
            List<Integer> roleIds = userVO.getRoleIds();
            if (CollectionUtil.isNotEmpty(roleIds)) {
                roleIds.forEach(x -> {
                    UserRoleEntity userRoleEntity = new UserRoleEntity();
                    userRoleEntity.setRoleId(x);
                    userRoleEntity.setUserId(userId);
                    userRoleService.save(userRoleEntity);
                });
            }
            user.setRoles(roleEntityList);
        }
        return user;
    }

    @Override
    public Boolean findUserByUserName(String userName) {
        if (StringUtils.isNotEmpty(userName)) {
            QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(SysUserEntity::getUserName, userName);
            List<SysUserEntity> sysUserEntityList = list(queryWrapper);
            if (CollectionUtil.isEmpty(sysUserEntityList)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateUser(SysUserVO userVo) {
        if (null != userVo) {
            SysUserEntity sysUserEntity = new SysUserEntity();
            BeanUtils.copyProperties(userVo, sysUserEntity);
            SysUserEntity currentUser = ShiroUtils.getCurrentUser();
            sysUserEntity.setUpdateUser(null == currentUser ? 0 : currentUser.getId());
            sysUserEntity.setPassword(PasswordUtill.encrypt(userVo.getUserName(), userVo.getPassword()));
            Boolean result = updateById(sysUserEntity);
            if (result) {
                List<Integer> roleIds = userVo.getRoleIds();

                if (CollectionUtil.isNotEmpty(roleIds)) {
                    //删除旧角色
                    QueryWrapper<UserRoleEntity> query = new QueryWrapper();
                    query.lambda().eq(UserRoleEntity::getUserId, userVo.getId());
                    List<UserRoleEntity> list = userRoleService.list(query);
                    if (CollectionUtil.isNotEmpty(list)) {
                        list.stream().forEach(x -> {
                            userRoleService.removeById(x);
                        });
                    }
                    //添加新角色
                    roleIds.stream().forEach(x -> {
                        UserRoleEntity userRoleEntity = new UserRoleEntity();
                        userRoleEntity.setUserId(sysUserEntity.getId());
                        userRoleEntity.setRoleId(x);
                        userRoleService.save(userRoleEntity);
                    });
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean deleteUser(Integer userId, int realDelete) {
        SysUserEntity sysUserEntity = getById(userId);
        if (realDelete == 0) {
            if (null != sysUserEntity) {
                sysUserEntity.setDeleteFlag(1);
                SysUserEntity currentUser = ShiroUtils.getCurrentUser();
                sysUserEntity.setUpdateUser(null == currentUser ? 0 : currentUser.getId());
                updateById(sysUserEntity);
            }
            return true;

        } else if (realDelete == 1) {
            if (null != sysUserEntity) {
                removeById(sysUserEntity);
                //删除用户的角色
                QueryWrapper<UserRoleEntity> queryWrapper = new QueryWrapper();
                queryWrapper.lambda().eq(UserRoleEntity::getUserId, sysUserEntity.getId());
                List<UserRoleEntity> userRoleEntityList = userRoleService.list(queryWrapper);
                if (CollectionUtil.isNotEmpty(userRoleEntityList)) {
                    userRoleEntityList.stream().forEach(x -> {
                        userRoleService.removeById(x);
                    });
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public IPage<SysUserVO> getUsers(Integer pageIndex, Integer pageSize, String userName, String realName, Integer substationId, String phone) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUserEntity::getDeleteFlag, 0);

        if (StringUtils.isNotEmpty(userName)) {
            queryWrapper.lambda().like(SysUserEntity::getUserName, userName);
        }
        if (StringUtils.isNotEmpty(realName)) {
            queryWrapper.lambda().like(SysUserEntity::getRealName, realName);
        }
        if (null != substationId) {
            queryWrapper.lambda().eq(SysUserEntity::getSubstationId, substationId);
        }
        if (StringUtils.isNotEmpty(phone)) {
            queryWrapper.lambda().like(SysUserEntity::getPhone, phone);
        }
        queryWrapper.lambda().orderByDesc(SysUserEntity::getCreateTime);
        Page<SysUserEntity> page = new Page(pageIndex, pageSize);
        IPage<SysUserEntity> userEntityIPage = page(page, queryWrapper);
        int total = count(queryWrapper);

        Page<SysUserVO> sysUserVOPage = new Page<>();
        List<SysUserVO> sysUserVOList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userEntityIPage.getRecords())) {
            userEntityIPage.getRecords().stream().forEach(x -> {
                SysUserVO sysUserVO = new SysUserVO();
                BeanUtils.copyProperties(x, sysUserVO);
                Set<SysRoleVO> sysRoleVOSet = new HashSet<>();

                //查找用户角色
                List<UserRoleVO> userRoleList = userRoleService.getUserRoles(x.getId());
                if (CollectionUtil.isNotEmpty(userRoleList)) {
                    userRoleList.stream().forEach(y -> {
                        SysRoleVO sysRoleVO = new SysRoleVO();
                        sysRoleVO.setId(y.getRoleId());
                        sysRoleVO.setDescription(y.getRoleDescription());
                        sysRoleVO.setName(y.getRoleName());
                        sysRoleVOSet.add(sysRoleVO);
                    });
                    sysUserVO.setRoles(sysRoleVOSet);
                }
                sysUserVOList.add(sysUserVO);
            });
        }
        sysUserVOPage.setRecords(sysUserVOList);
        sysUserVOPage.setSize(pageSize);
        sysUserVOPage.setCurrent(pageIndex);
        sysUserVOPage.setTotal(total);
        return sysUserVOPage;
    }
}
