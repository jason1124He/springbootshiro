package com.sunppenergy.config;

import com.sunppenergy.entity.SysAuthEntity;
import com.sunppenergy.entity.SysRoleEntity;
import com.sunppenergy.entity.SysUserEntity;
import com.sunppenergy.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义relam，实现授权和认证
 *
 * @Author: hej
 * @Date: 2019/7/17 9:25
 * @Description:
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private SysUserService sysUserService;

    /**
     * 执行授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("----------------执行授权开始------------------");

        Subject subject = SecurityUtils.getSubject();
        SysUserEntity sysUser = (SysUserEntity) subject.getPrincipal();
        if (null != sysUser) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //权限 角色 字符串集合
            Collection<String> rolesCollection = new HashSet();
            Collection<String> authsCollection = new HashSet<>();

            // 读取并赋值用户角色与权限
            Set<SysRoleEntity> roles = sysUser.getRoles();
            for (SysRoleEntity role : roles) {
                rolesCollection.add(role.getName());
                Set<SysAuthEntity> auths = role.getAuths();
                for (SysAuthEntity auth : auths) {
                    authsCollection.add(auth.getUrl());
                }
                info.addStringPermissions(authsCollection);
            }
            info.addRoles(rolesCollection);
            logger.info("----------------执行授权结束------------------");
            return info;
        }

        return null;
    }


    /**
     * 用户认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("----------------执行用户认证开始------------------");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SysUserEntity user = sysUserService.findByName(token.getUsername());

        if (null == user) {
            logger.info("----------------未知用户------------------");
            throw new UnknownAccountException("未知用户");
        }

//        SysUserEntity userEntity = new SysUserEntity();
//        userEntity.setUserName(user.getUserName());
//        userEntity.setPassword(user.getPassword());
//        BeanUtil.copyProperties(user,userEntity);

        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserName());
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userEntity, user.getPassword(), credentialsSalt, getName());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());


        logger.info("----------------执行用户认证结束------------------");
        return authenticationInfo;
    }


    public static void main(String[] args) {
        String username = "admin";
        String password = "123456";
        String hashAlgorithName = "MD5";
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        System.out.println(obj);
//        System.out.println("\u767b\u5f55\u5931\u8d25\uff01");

    }

}
