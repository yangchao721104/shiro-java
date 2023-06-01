package com.yang.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author yang
 * @date 2023/6/1 4:56
 */
@Controller
@RequestMapping("/myController")
public class MyController {

    @GetMapping("/login")
    public String login(){
        return"login";
    }

    @GetMapping("/userLogin")
    public String userLogin(String name, String pwd,
                            @RequestParam(defaultValue = "false")boolean rememberMe,
                            HttpSession session){
        //获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //获取请求数据到token
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd,rememberMe);
        //调用login方法进行登录认证
        try {
            subject.login(token);
//            return "登录成功";
            session.setAttribute("user",token.getPrincipal().toString());
            return "main";
        }catch (AuthenticationException e){
            e.printStackTrace();
            System.out.println("登录失败");
            return "登录失败";
        }
    }

    @RequestMapping("/userLoginMe")
    public String userLogin(HttpSession httpSession){
        httpSession.setAttribute("user","rememberMe");
        return "main";
    }

    //登录认证验证角色
    @RequiresRoles("admin")
    @GetMapping("/userLoginRoles")
    @ResponseBody
    public String userLoginRoles(){
        System.out.println("登录认证验证角色");
        return "验证角色成功";
    }

}
