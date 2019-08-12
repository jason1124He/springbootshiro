package com.sunppenergy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunppenergy.entity.RoleAuthEntity;
import com.sunppenergy.dao.RoleAuthDao;
import com.sunppenergy.service.RoleAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunppenergy.vo.RoleAuthVO;
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
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthDao, RoleAuthEntity> implements RoleAuthService {


    @Autowired
    private RoleAuthDao roleAuthDao;

    @Override
    public List<RoleAuthVO> getRoleAndAuths(Integer roleId) {
        return roleAuthDao.getRoleAndAuths(roleId);
    }
}
