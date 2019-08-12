package com.sunppenergy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunppenergy.common.JSONResult;
import com.sunppenergy.common.ResultConstants;
import com.sunppenergy.entity.RoleAuthEntity;
import com.sunppenergy.entity.SysAuthEntity;
import com.sunppenergy.dao.SysAuthDao;
import com.sunppenergy.service.RoleAuthService;
import com.sunppenergy.service.SysAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunppenergy.utils.StringUtils;
import com.sunppenergy.vo.SysAuthVO;
import com.sunppenergy.vo.SysRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
@Service
public class SysAuthServiceImpl extends ServiceImpl<SysAuthDao, SysAuthEntity> implements SysAuthService {

    @Autowired
    private RoleAuthService roleAuthService;

    /**
     * 获取权限列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页数据条数
     * @param name      权限名称
     * @param url       权限路径
     * @returns
     */
    @Override
    public IPage<SysAuthVO> getAuths(Integer pageIndex, Integer pageSize, String name, String url) {
        QueryWrapper<SysAuthEntity> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysAuthEntity::getDeleteFlag, 0);
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.lambda().like(SysAuthEntity::getName, name);
        }
        if (StringUtils.isNotEmpty(url)) {
            queryWrapper.lambda().like(SysAuthEntity::getUrl, url);
        }
        queryWrapper.lambda().orderByDesc(SysAuthEntity::getCreateTime);
        Page<SysAuthEntity> page = new Page(pageIndex, pageSize);
        IPage<SysAuthEntity> authList = page(page, queryWrapper);
        Integer total = count(queryWrapper);

        Page<SysAuthVO> sysAuthVOPage = new Page<>();
        List<SysAuthVO> sysAuthVOList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(authList.getRecords())) {
            authList.getRecords().stream().forEach(x -> {
                SysAuthVO sysRoleVO = new SysAuthVO();
                BeanUtil.copyProperties(x, sysRoleVO);
                sysAuthVOList.add(sysRoleVO);
            });
        }
        sysAuthVOPage.setRecords(sysAuthVOList);
        sysAuthVOPage.setTotal(total);
        sysAuthVOPage.setCurrent(pageIndex);
        sysAuthVOPage.setSize(pageSize);
        return sysAuthVOPage;
    }

    @Override
    public List<SysAuthVO> getAllAuths(String name, String url) {

        QueryWrapper<SysAuthEntity> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysAuthEntity::getDeleteFlag, 0);
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.lambda().like(SysAuthEntity::getName, name);
        }
        if (StringUtils.isNotEmpty(url)) {
            queryWrapper.lambda().like(SysAuthEntity::getUrl, url);
        }
        queryWrapper.lambda().orderByDesc(SysAuthEntity::getCreateTime);

        List<SysAuthEntity> authList = list(queryWrapper);
        List<SysAuthVO> authVOList = new ArrayList<>();
        authList.stream().forEach(x -> {
            SysAuthVO vo = new SysAuthVO();
            BeanUtil.copyProperties(x, vo);
            authVOList.add(vo);
        });
        return authVOList;
    }

    @Override
    public Boolean deleteAuth(Integer id) {

        SysAuthEntity sysAuthEntity = getById(id);
        if (null != sysAuthEntity) {
            Boolean result = removeById(sysAuthEntity);
            if (result) {
                //删除相关角色的权限
                QueryWrapper<RoleAuthEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(RoleAuthEntity::getAuthId, id);
                List<RoleAuthEntity> roleAuthEntityList = roleAuthService.list();
                if (CollectionUtil.isNotEmpty(roleAuthEntityList)) {
                    roleAuthEntityList.stream().forEach(roleAuthEntity -> {
                        roleAuthService.removeById(roleAuthEntity.getId());
                    });
                }
            }
            return true;
        }
        return false;
    }


}
