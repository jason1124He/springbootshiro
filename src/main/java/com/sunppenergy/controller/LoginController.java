package com.sunppenergy.controller;

import com.sunppenergy.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: hej
 * @Date: 2019/7/17 10:56
 * @Description:
 */
@Controller
@ResponseBody
public class LoginController {
    /**
     * 个人中心，需认证可访问
     */
    @RequestMapping("/user/index")
    public String add(HttpServletRequest request) {
//        SysUserEntity bean = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
//        request.setAttribute("userName", bean.getUserName());
        return "/user/index111";
    }

    /**
     * 会员中心，需认证且角色为vip可访问
     */
    @RequestMapping("/vip/index")
    public String update() {
        return "/vip/index";
    }

}
