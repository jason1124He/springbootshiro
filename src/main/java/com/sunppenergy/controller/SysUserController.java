package com.sunppenergy.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sunppenergy.common.Constans;
import com.sunppenergy.common.JSONResult;
import com.sunppenergy.common.ResultConstants;
import com.sunppenergy.dao.SysUserDao;
import com.sunppenergy.entity.SysAuthEntity;
import com.sunppenergy.entity.SysRoleEntity;
import com.sunppenergy.entity.SysUserEntity;
import com.sunppenergy.entity.UserRoleEntity;
import com.sunppenergy.service.*;
import com.sunppenergy.utils.PasswordUtill;
import com.sunppenergy.utils.StringUtils;
import com.sunppenergy.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hej
 * @since 2019-07-17
 */
@Api(value = "系统用户操作控制器", tags = "SysUserController")
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleAuthService roleAuthService;

    @Autowired
    private SysAuthService sysAuthService;

    /**
     * 新增用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    @ApiOperation(value = "增加用户")
    public JSONResult saveUser(@RequestBody SysUserVO userVo) {

        SysUserEntity userEntity = sysUserService.saveUserAndRole(userVo);
        if (null != userEntity) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    /**
     * 修改用户（包含逻辑删除）,包含修改用户角色
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户包（含逻辑删除，逻辑删除将deleteFlag设为1）,包含修改用户角色")
    public JSONResult updateUser(@RequestBody SysUserVO userVo) {
        Boolean result = sysUserService.updateUser(userVo);
        if (result) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    @ApiOperation(value = "删除用户信息")
    public JSONResult deleteUser(@RequestParam Integer userId) {

        Boolean result = sysUserService.deleteUser(userId, 0);
        if (result) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    @ApiOperation(value = "查找用户分页信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", paramType = "query", value = "页码", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", value = "每页数据条数", defaultValue = "10", required = true),
            @ApiImplicitParam(name = "userName", paramType = "query", value = "用户登录名称", required = false),
            @ApiImplicitParam(name = "realName", paramType = "query", value = "用户真实姓名", required = false),
            @ApiImplicitParam(name = "substationId", paramType = "query", value = "用户所属变电所id", required = false),
            @ApiImplicitParam(name = "phone", paramType = "query", value = "用户联系号码", required = false)
    })
    public JSONResult getUsers(Integer pageIndex, Integer pageSize, String userName, String realName, Integer substationId, String phone) {
        IPage<SysUserVO> sysUserVOIPage = sysUserService.getUsers(pageIndex, pageSize, userName, realName, substationId, phone);
        if (null != sysUserVOIPage) {
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE, sysUserVOIPage);
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_MESSAGE);
    }

    @RequestMapping(value = "/checkUserName", method = RequestMethod.GET)
    @ApiOperation(value = "用户名重复性校验")
    public JSONResult checkUserName(@RequestParam String userName) {
        if (StringUtils.isNotEmpty(userName)) {
            Boolean result = sysUserService.findUserByUserName(userName);
            if (result) {
                return new JSONResult(ResultConstants.SUCCESS, ResultConstants.NOT_REPEATED);
            } else if (!result) {
                return new JSONResult(ResultConstants.FAILED, ResultConstants.REPEATED);
            }
        }
        return new JSONResult(ResultConstants.FAILED, ResultConstants.NULL);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    public JSONResult login(@RequestBody SysUserVO userVO, HttpServletRequest request, HttpServletResponse response) {

        SysUserVO sysUserVO = new SysUserVO();
//        response.setHeader("root", request.getContextPath());
//        String userName = request.getParameter("username");
//        String password = request.getParameter("password");
        String userName = userVO.getUserName();
        String password = userVO.getPassword();

        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUserEntity::getUserName, userName);
        queryWrapper.lambda().eq(SysUserEntity::getDeleteFlag, 0);
        queryWrapper.lambda().eq(SysUserEntity::getPassword, PasswordUtill.encrypt(userName, password));
        SysUserEntity sysUserEntity = sysUserService.getOne(queryWrapper);
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();

//        //保存用户信息到session
        Session session = subject.getSession();
        session.setAttribute(Constans.WBB_USER, sysUserEntity);
        //设置session过期时间为30分钟
        session.setTimeout(30 * 1000 * 3600);

//        HttpSession httpSession =request.getSession();
//        httpSession.setAttribute(Constans.WBB_USER, sysUserEntity);
//        //设置session过期时间为30分钟
//        httpSession.setMaxInactiveInterval(30 * 60 * 1000);
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        // 3.执行登录方法
        try {
            subject.login(token);

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            request.setAttribute("msg", "用户名不存在！");
        } catch (IncorrectCredentialsException e) {
            request.setAttribute("msg", "密码错误！");
        }

        if (subject.isAuthenticated()) {
            //获取用户权限
            List<UserRoleVO> userRoleVOList = userRoleService.getUserRoles(sysUserEntity.getId());
            Set<SysAuthVO> authVOSet = new HashSet<>();
            Set<SysRoleVO> roleVOSet = new HashSet<>();
            Set<Integer> authIds = new HashSet<>();
            Set<Integer> roleIds = new HashSet<>();
            if (CollectionUtil.isNotEmpty(userRoleVOList)) {
                userRoleVOList.stream().forEach(x -> {
                    //获取角色信息
                    roleIds.add(x.getRoleId());
                    if (CollectionUtil.isNotEmpty(roleIds)) {
                        roleIds.stream().forEach(roleId -> {
                            QueryWrapper<SysRoleEntity> query = new QueryWrapper();
                            query.lambda().eq(SysRoleEntity::getId, roleId);
                            query.lambda().eq(SysRoleEntity::getDeleteFlag, 0);
                            SysRoleEntity sysRoleEntity = sysRoleService.getOne(query);
                            if (null != sysRoleEntity) {
                                SysRoleVO sysRoleVO = new SysRoleVO();
                                BeanUtils.copyProperties(sysRoleEntity, sysRoleVO);
                                roleVOSet.add(sysRoleVO);
                            }
                        });
                    }

                    //获取权限信息
                    List<RoleAuthVO> roleAuthVOList = roleAuthService.getRoleAndAuths(x.getRoleId());
                    if (CollectionUtil.isNotEmpty(roleAuthVOList)) {
                        roleAuthVOList.stream().forEach(y -> {
                            authIds.add(y.getAuthId());
                        });
                    }
                });
                if (CollectionUtil.isNotEmpty(authIds)) {
                    authIds.stream().forEach(authId -> {
                        QueryWrapper<SysAuthEntity> query = new QueryWrapper();
                        query.lambda().eq(SysAuthEntity::getId, authId);
                        query.lambda().eq(SysAuthEntity::getDeleteFlag, 0);
                        SysAuthEntity sysAuthEntity = sysAuthService.getOne(query);
                        SysAuthVO sysAuthVO = new SysAuthVO();
                        if (null != sysAuthEntity) {
                            BeanUtils.copyProperties(sysAuthEntity, sysAuthVO);
                            authVOSet.add(sysAuthVO);
                        }
                    });
                }
                BeanUtils.copyProperties(sysUserEntity, sysUserVO);
                sysUserVO.setAuths(authVOSet);
                sysUserVO.setRoles(roleVOSet);
            }
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_LOGIN, sysUserVO);
        }

        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_LOGIN);
    }


    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    public JSONResult logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_MESSAGE);
    }
}
