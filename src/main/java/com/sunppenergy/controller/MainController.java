package com.sunppenergy.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunppenergy.common.Constans;
import com.sunppenergy.common.JSONResult;
import com.sunppenergy.common.ResultConstants;
import com.sunppenergy.entity.SysUserEntity;
import com.sunppenergy.service.RoleAuthService;
import com.sunppenergy.service.SysRoleService;
import com.sunppenergy.service.SysUserService;
import com.sunppenergy.service.UserRoleService;
import com.sunppenergy.utils.PasswordUtill;
import com.sunppenergy.vo.RoleAuthVO;
import com.sunppenergy.vo.SysUserVO;
import com.sunppenergy.vo.UserRoleVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: hej
 * @Date: 2019/7/17 10:58
 * @Description:
 */
@Controller
public class MainController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private UserRoleService userRoleService;


    @Autowired
    private RoleAuthService roleAuthService;

    @RequestMapping("/main")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("root", request.getContextPath());
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("root", request.getContextPath());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONResult login(@RequestBody SysUserVO userVO, HttpServletRequest request, HttpServletResponse response) {

        SysUserVO sysUserVO = new SysUserVO();
//        response.setHeader("root", request.getContextPath());
//        String userName = request.getParameter("username");
//        String password = request.getParameter("password");
        String userName = userVO.getUserName();
        String password = userVO.getPassword();

        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUserEntity::getUserName, userName);
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
            Set<RoleAuthVO> roleAuthVOSet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(userRoleVOList)) {
                userRoleVOList.stream().forEach(x -> {
                    List<RoleAuthVO> roleAuthVOList = roleAuthService.getRoleAndAuths(x.getRoleId());
                    if (CollectionUtil.isNotEmpty(roleAuthVOList)) {
                        roleAuthVOList.stream().forEach(y -> {
                            roleAuthVOSet.add(y);
                        });
                    }
                });
                BeanUtils.copyProperties(sysUserEntity, sysUserVO);
//                sysUserVO.setAuths(roleAuthVOSet);
            }
            return new JSONResult(ResultConstants.SUCCESS, ResultConstants.SUCCESS_LOGIN, sysUserVO);
        }

        return new JSONResult(ResultConstants.FAILED, ResultConstants.FAILED_LOGIN);
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "redirect:/main";
    }

    @RequestMapping("/error/unAuth")
    public String unAuth() {
        return "/error/unAuth";
    }
}
