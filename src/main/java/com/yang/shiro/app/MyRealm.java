package com.yang.shiro.app;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * 自定义登录认证
 * @author yang
 * @date 2023/6/1 3:07
 */
public class MyRealm extends AuthenticatingRealm {

    /**
     * shiro的login方法底层会调用登录认证方法
     * 需要配置自定义的realm生效，1。在ini文件配置，2.springboot中配置
     * 该方法只是获取进行对比的信息，认证逻辑还是shiro的底层逻辑
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {

        //1.获取身份信息
        String principal = token.getPrincipal().toString();
        //2.获取凭证信息
        String password = new String((char[]) token.getCredentials());
        System.out.println("认证用户信息="+principal+"--------"+password);
        //3.获取数据库中存储的用户信息
        if (principal.equals("zhangSan")){
            //数据库红存储的加盐3次迭代密码
            String pwdInfo="07ca00e10899418f0ea4ab92a9d69065";
            //4.创建封装校验逻辑对象，封装数据返回
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(),
                    pwdInfo,
                    ByteSource.Util.bytes("salt"),
                    token.getPrincipal().toString());

            return info;
        }
        return null;
    }

}
