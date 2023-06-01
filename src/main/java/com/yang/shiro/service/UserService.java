package com.yang.shiro.service;

import com.yang.shiro.entity.User;

import java.util.List;

/**
 * @author yang
 * @date 2023/6/1 4:17
 */
public interface UserService {

    //获取用户角色的权限信息
    List<String> getUserPermissionInfo(List<String> roles);

    //根据用户查询角色信息
    List<String> getUserRoleInfo(String principal);

    //用户登录
    User getUserInfoByName(String name);
}
