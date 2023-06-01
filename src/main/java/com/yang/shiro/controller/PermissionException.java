package com.yang.shiro.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yang
 * @date 2023/6/1 15:36
 */

@ControllerAdvice
public class PermissionException {

    @ResponseBody
    @ExceptionHandler(UnauthenticatedException.class)
    public String unauthenticatedException(Exception e){
        return "无权限";
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(Exception e){
        return "权限认证失败";
    }

}
