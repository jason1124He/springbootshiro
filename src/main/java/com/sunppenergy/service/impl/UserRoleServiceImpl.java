package com.sunppenergy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunppenergy.entity.UserRoleEntity;
import com.sunppenergy.dao.UserRoleDao;
import com.sunppenergy.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunppenergy.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hej
 * @since 2019-07-26
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleEntity> implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<UserRoleVO> getUserRoles(Integer userId) {
        return userRoleDao.getUserRoles(userId);
    }
}
