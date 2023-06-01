package com.yang.shiro.app;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;


/**
 * shiro的第一个例子
 * @author yang
 * @date 2022/5/30 8:08
 */
public class HelloShiro {

    @Test
    public void shiroLogin(){
        //导入ini配置创建工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //使用subject主体去登录

        //工厂构建安全管理器
        SecurityManager securityManager = factory.getInstance();

        //使用工具生效安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //使用工具获得subject主体
        Subject subject = SecurityUtils.getSubject();
        //构建账户密码
        UsernamePasswordToken passwordToken = new UsernamePasswordToken("zhangSan", "123");
        subject.login(passwordToken);
        //打印登录登录主体
        System.out.println("登录结果："+subject.isAuthenticated());

        //判断角色
        boolean role1 = subject.hasRole("role1");
        System.out.printf("是否拥有此角色="+ role1 );

        //判断权限
        boolean permitted = subject.isPermitted("user:insert");
        System.out.println("判断权限 = " + permitted);

        //也可以用checkPermission方法，但是没有返回值，没权限抛异常
        subject.checkPermission("user:select");

    }


}
