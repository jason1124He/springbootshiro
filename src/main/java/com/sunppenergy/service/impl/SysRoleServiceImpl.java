package com.sunppenergy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunppenergy.common.ResultConstants;
import com.sunppenergy.common.ShiroUtils;
import com.sunppenergy.entity.RoleAuthEntity;
import com.sunppenergy.entity.SysAuthEntity;
import com.sunppenergy.entity.SysRoleEntity;
import com.sunppenergy.dao.SysRoleDao;
import com.sunppenergy.entity.SysUserEntity;
import com.sunppenergy.service.RoleAuthService;
import com.sunppenergy.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunppenergy.utils.StringUtils;
import com.sunppenergy.vo.RoleAuthVO;
import com.sunppenergy.vo.SysAuthVO;
import com.sunppenergy.vo.SysRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleInfo;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    @Autowired
    private RoleAuthService roleAuthService;

    @Override
    public Boolean saveRole(SysRoleVO sysRoleVO) {

        if (null != sysRoleVO) {
            SysRoleEntity roleEntity = new SysRoleEntity();
            BeanUtil.copyProperties(sysRoleVO, roleEntity);
            SysUserEntity sysUserEntity = ShiroUtils.getCurrentUser();
            roleEntity.setCreateUser(null == sysUserEntity ? 0 : sysUserEntity.getId());
            boolean result = save(roleEntity);
            if (result) {
                Integer roleId = roleEntity.getId();
                List<Integer> authIds = sysRoleVO.getAuthIds();
                if (CollectionUtil.isNotEmpty(authIds)) {
                    authIds.stream().forEach(x -> {
                        RoleAuthEntity roleAuthEntity = new RoleAuthEntity();
                        roleAuthEntity.setRoleId(roleId);
                        roleAuthEntity.setAuthId(x);
                        roleAuthService.save(roleAuthEntity);
                    });
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 删除角色
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteRoleByIds(List<Integer> ids, Integer realDelete) {
        if (realDelete == 0) {
            //逻辑删除
            if (CollectionUtil.isNotEmpty(ids)) {
                List<SysRoleEntity> roleEntityList = (List<SysRoleEntity>) listByIds(ids);
                if (CollectionUtil.isNotEmpty(roleEntityList)) {
                    SysUserEntity sysUserEntity = ShiroUtils.getCurrentUser();
                    roleEntityList.stream().forEach(x -> {
                        x.setDeleteFlag(1);
                        x.setUpdateUser(null == sysUserEntity ? 0 : sysUserEntity.getId());
                        x.setUpdateTime(new Date());
                        updateById(x);
                    });
                    return true;
                }
            }
        } else if (realDelete == 1) {
            //物理删除
            if (CollectionUtil.isNotEmpty(ids)) {
                boolean result = removeByIds(ids);
                if (result) {
                    List<RoleAuthEntity> roleAuths = (List<RoleAuthEntity>) roleAuthService.listByIds(ids);
                    if (CollectionUtil.isNotEmpty(roleAuths)) {
                        roleAuths.stream().forEach(x -> {
                            roleAuthService.removeById(x.getId());
                        });
                    }
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public Boolean updateRoleById(SysRoleVO sysRoleVO) {
        if (null != sysRoleVO) {
            //更新角色信息
            SysRoleEntity sysRoleEntity = new SysRoleEntity();
            BeanUtil.copyProperties(sysRoleVO, sysRoleEntity);
            SysUserEntity sysUserEntity = ShiroUtils.getCurrentUser();
            sysRoleEntity.setUpdateUser(null == sysUserEntity ? 0 : sysUserEntity.getId());
            Boolean result = updateById(sysRoleEntity);
            //更新角色的权限
            if (result) {
                Integer roleId = sysRoleVO.getId();
                //添加新权限
                List<Integer> authIds = sysRoleVO.getAuthIds();
                if (CollectionUtil.isNotEmpty(authIds)) {
                    //删除原有权限
                    QueryWrapper<RoleAuthEntity> query = new QueryWrapper();
                    query.lambda().eq(RoleAuthEntity::getRoleId, sysRoleVO.getId());
                    List<RoleAuthEntity> list = roleAuthService.list(query);
                    if (CollectionUtil.isNotEmpty(list)) {
                        list.stream().forEach(x -> {
                            roleAuthService.removeById(x.getId());
                        });
                    }

                    authIds.stream().forEach(x -> {
                        RoleAuthEntity roleAuthEntity = new RoleAuthEntity();
                        roleAuthEntity.setAuthId(x);
                        roleAuthEntity.setRoleId(roleId);
                        roleAuthService.save(roleAuthEntity);
                    });
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public IPage<SysRoleVO> getRoles(Integer pageIndex, Integer pageSize, String name) {

        QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysRoleEntity::getDeleteFlag, 0);
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.lambda().like(SysRoleEntity::getName, name);
        }
        queryWrapper.lambda().orderByDesc(SysRoleEntity::getCreateTime);
        Page<SysRoleEntity> page = new Page(pageIndex, pageSize);
        IPage<SysRoleEntity> roleList = page(page, queryWrapper);
        Integer total = count(queryWrapper);

        Page<SysRoleVO> sysRoleVOPage = new Page<>();
        List<SysRoleVO> sysRoleVOList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(roleList.getRecords())) {
            roleList.getRecords().stream().forEach(x -> {
                Set<SysAuthVO> authVOSet = new HashSet<>();
                SysRoleVO sysRoleVO = new SysRoleVO();
                BeanUtil.copyProperties(x, sysRoleVO);

                //查找角色的权限
                QueryWrapper<RoleAuthEntity> query = new QueryWrapper();
                query.lambda().eq(RoleAuthEntity::getRoleId, x.getId());
                List<RoleAuthVO> roleAuthVOList = roleAuthService.getRoleAndAuths(x.getId());
                if (CollectionUtil.isNotEmpty(roleAuthVOList)) {
                    roleAuthVOList.stream().forEach(y -> {
                        SysAuthVO sysAuthVO = new SysAuthVO();
                        sysAuthVO.setId(y.getAuthId());
                        sysAuthVO.setName(y.getName());
                        sysAuthVO.setUrl(y.getUrl());
                        authVOSet.add(sysAuthVO);
                    });
                    sysRoleVO.setAuths(authVOSet);
                }
                sysRoleVOList.add(sysRoleVO);
            });
        }
        sysRoleVOPage.setRecords(sysRoleVOList);
        sysRoleVOPage.setTotal(total);
        sysRoleVOPage.setCurrent(pageIndex);
        sysRoleVOPage.setSize(pageSize);
        return sysRoleVOPage;
    }

    @Override
    public List<SysRoleVO> getAllRoles(String name) {

        QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysRoleEntity::getDeleteFlag, 0);
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.lambda().like(SysRoleEntity::getName, name);
        }
        List<SysRoleEntity> sysRoleEntityList = list(queryWrapper);
        List<SysRoleVO> sysRoleVOList = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(sysRoleEntityList)) {
            sysRoleEntityList.stream().forEach(x -> {
                Set<SysAuthVO> authVOSet = new HashSet<>();
                SysRoleVO sysRoleVO = new SysRoleVO();
                BeanUtil.copyProperties(x, sysRoleVO);

                //查找角色的权限
                QueryWrapper<RoleAuthEntity> query = new QueryWrapper();
                query.lambda().eq(RoleAuthEntity::getRoleId, x.getId());
                List<RoleAuthVO> roleAuthVOList = roleAuthService.getRoleAndAuths(x.getId());
                if (CollectionUtil.isNotEmpty(roleAuthVOList)) {
                    roleAuthVOList.stream().forEach(y -> {
                        SysAuthVO sysAuthVO = new SysAuthVO();
                        sysAuthVO.setId(y.getAuthId());
                        sysAuthVO.setName(y.getName());
                        sysAuthVO.setUrl(y.getUrl());
                        authVOSet.add(sysAuthVO);
                    });
                    sysRoleVO.setAuths(authVOSet);
                }
                sysRoleVOList.add(sysRoleVO);
            });
        }
        return sysRoleVOList;
    }
}
