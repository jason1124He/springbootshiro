package com.sunppenergy.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 自定义密码加密工具类
 *
 * @Author: admin
 * @Date: 2019/7/29 15:32
 * @Description:
 */
public class PasswordUtill {

    /**
     * 结合用户名和密码混合加密
     *
     * @param username
     * @param password
     * @return
     */
    public static String encrypt(String username, String password) {
        String hashAlgorithName = "MD5";
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        return obj.toString();
    }
}
