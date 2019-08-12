package com.sunppenergy.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sunppenergy.common.JSONResult;
import com.sunppenergy.common.ResultConstants;
import com.sunppenergy.service.SysRoleService;
import com.sunppenergy.vo.SysAuthVO;
import com.sunppenergy.vo.SysRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/role")
@Api(value = "角色信息控类", tags = "SysRoleController")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;


    /**
     * 新建角色，并分配权限
     *
     * @param sysRoleVO
     * @return
     */
    @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    @ApiOperation(value = "新建角色，并分配权限")
    public JSONResult saveRole(@RequestBody SysRoleVO sysRoleVO) {
        Boolean result = sysRoleService.saveRole(sysRoleVO);
        if (result) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    /**
     * 删除角色
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteRoleByIds", method = RequestMethod.POST)
    @ApiOperation(value = "逻辑删除角色")
    public JSONResult deleteRoleByIds(@RequestBody List<Integer> ids) {
        Boolean result = sysRoleService.deleteRoleByIds(ids, 0);
        if (result) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    @ApiOperation(value = " 更新角色")
    public JSONResult updateRoleById(@RequestBody SysRoleVO sysRoleVO) {
        Boolean result = sysRoleService.updateRoleById(sysRoleVO);
        if (result) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }


    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    @ApiOperation(value = "根据条件获取角色分页信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", paramType = "query", value = "页码", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", value = "每页数据条数", defaultValue = "10", required = true),
            @ApiImplicitParam(name = "name", paramType = "query", value = "角色名称,可不传", required = false)
    })
    public JSONResult getRoles(Integer pageIndex, Integer pageSize, String name) {
        IPage<SysRoleVO> list = sysRoleService.getRoles(pageIndex, pageSize, name);
        if (null != list) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE, list);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }


    @RequestMapping(value = "getAllRolesByFilter", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", paramType = "query", value = "权限名称,可不传", required = false)
    })
    public JSONResult getAllAuhtsByFilter(String name) {
        List<SysRoleVO> roleVOList = sysRoleService.getAllRoles(name);
        if (null != roleVOList) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE, roleVOList);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }
}
