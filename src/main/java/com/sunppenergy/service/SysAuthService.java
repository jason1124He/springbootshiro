package com.sunppenergy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sunppenergy.entity.SysAuthEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunppenergy.vo.SysAuthVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
public interface SysAuthService extends IService<SysAuthEntity> {

    /**
     * 获取权限列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页数据条数
     * @param name      权限名称
     * @param url       权限路径
     * @return
     */
    IPage<SysAuthVO> getAuths(Integer pageIndex, Integer pageSize, String name, String url);

    /**
     * 获取所有权限信息
     *
     * @return
     */
    List<SysAuthVO> getAllAuths(String name, String url);

    /**
     * 物理删除权限信息
     * @param id
     * @return
     */
    Boolean deleteAuth(Integer id);
}
