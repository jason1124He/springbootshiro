package com.sunppenergy.common;

import com.sunppenergy.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;

/**
 * @Author: admin
 * @Date: 2019/7/26 15:55
 * @Description:
 */
public class ShiroUtils {
    public static SysUserEntity getCurrentUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }
}
