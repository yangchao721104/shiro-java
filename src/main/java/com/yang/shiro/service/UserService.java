package com.yang.shiro.service;

import com.yang.shiro.entity.User;

/**
 * @author yang
 * @date 2023/6/1 4:17
 */
public interface UserService {

    //用户登录
    User getUserInfoByName(String name);
}
