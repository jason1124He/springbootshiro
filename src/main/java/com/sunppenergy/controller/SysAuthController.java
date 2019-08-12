package com.sunppenergy.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunppenergy.common.JSONResult;
import com.sunppenergy.common.ResultConstants;
import com.sunppenergy.common.ShiroUtils;
import com.sunppenergy.entity.SysAuthEntity;
import com.sunppenergy.service.SysAuthService;
import com.sunppenergy.utils.StringUtils;
import com.sunppenergy.vo.SysAuthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/sys/auth")
@Api(value = "系统权限控制类", tags = "SysAuthController")
public class SysAuthController {

    @Autowired
    private SysAuthService sysAuthService;

    /**
     * 新增权限
     *
     * @param sysAuthVO
     * @return
     */
    @RequestMapping(value = "saveAuth", method = RequestMethod.POST)
    @ApiOperation(value = "新增权限")
    public JSONResult saveAuth(@RequestBody SysAuthVO sysAuthVO) {
        if (null != sysAuthVO) {
            SysAuthEntity sysAuthEntity = new SysAuthEntity();
            BeanUtils.copyProperties(sysAuthVO, sysAuthEntity);
            sysAuthEntity.setCreateUser(null == ShiroUtils.getCurrentUser() ? 0 : ShiroUtils.getCurrentUser().getId());
            boolean result = sysAuthService.save(sysAuthEntity);
            if (result) {
                return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE, sysAuthEntity.getId());
            }
        }

        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    /**
     * 更新权限
     *
     * @param sysAuthVO
     * @return
     */
    @RequestMapping(value = "/updateAuth", method = RequestMethod.POST)
    @ApiOperation(value = "更新权限,删除时，请设deleteFlag=1")
    public JSONResult updateAuth(@RequestBody SysAuthVO sysAuthVO) {
        if (null != sysAuthVO) {
            SysAuthEntity sysAuthEntity = new SysAuthEntity();
            BeanUtils.copyProperties(sysAuthVO, sysAuthEntity);
            sysAuthEntity.setUpdateUser(null == ShiroUtils.getCurrentUser() ? 0 : ShiroUtils.getCurrentUser().getId());
            boolean result = sysAuthService.updateById(sysAuthEntity);
            if (result) {
                return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE);
            }
        }

        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    @RequestMapping(value = "/deleteAuth", method = RequestMethod.GET)
    @ApiOperation(value = "物理删除权限信息")
    public JSONResult deleteAuth(@RequestParam Integer id) {

        Boolean result = sysAuthService.deleteAuth(id);
        if (result) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    @RequestMapping(value = "/getAuths", method = RequestMethod.GET)
    @ApiOperation(value = "根据条件获取权限的分页信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", paramType = "query", value = "页码", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", value = "每页数据条数", defaultValue = "10", required = true),
            @ApiImplicitParam(name = "name", paramType = "query", value = "权限名称,可不传", required = false),
            @ApiImplicitParam(name = "url", paramType = "query", value = "权限路径,可不传", required = false)
    })
    public JSONResult getAuths(Integer pageIndex, Integer pageSize, String name, String url) {

        IPage<SysAuthVO> authList = sysAuthService.getAuths(pageIndex, pageSize, name, url);
        if (null != authList) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE, authList);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    @RequestMapping(value = "/getAuthById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取权限信息")
    public JSONResult getAuthById(@RequestParam(name = "id") Integer id) {
        SysAuthEntity auth = sysAuthService.getById(id);
        SysAuthVO authVO = new SysAuthVO();
        BeanUtil.copyProperties(auth, authVO);
        if (null != auth) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE, authVO);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    @RequestMapping(value = "getAllAuthsByFilter", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", paramType = "query", value = "权限名称,可不传", required = false),
            @ApiImplicitParam(name = "url", paramType = "query", value = "权限路径,可不传", required = false)
    })
    public JSONResult getAllAuhtsByFilter(String name, String url) {
        List<SysAuthVO> authVOList = sysAuthService.getAllAuths(name, url);
        if (null != authVOList) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE, authVOList);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

}
