package com.yang.shiro.realm;

import com.yang.shiro.entity.User;
import com.yang.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yang
 * @date 2023/6/1 4:36
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 自定义授权方法：获取当前登录用户的角色、权限信息，返回给shiro用来进行权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("自定义授权方法");

        String principal = principalCollection.getPrimaryPrincipal().toString();
        //获取当前登录用户的角色信息
        List<String> roles = userService.getUserRoleInfo(principal);
        System.out.println("当前登录用户的角色信息 = " + roles);
        //获取当前登录用户的权限信息
        List<String> permissions = userService.getUserPermissionInfo(roles);
        System.out.println("当前登录用户的权限信息 = " + permissions);

        //创建对象，封装当前登录用户的角色、权限信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //存储角色
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(permissions);

        return authorizationInfo;
    }

    /**
     * 自定义登录认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        //1获取用户身份信息
        String name = authenticationToken.getPrincipal().toString();

        //调用业务层获取用户信息
        User user = userService.getUserInfoByName(name);

        if (user !=null){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    user.getPwd(),
                    ByteSource.Util.bytes("salt"),
                    authenticationToken.getPrincipal().toString()
            );
            return info;
        }
        return null;
    }
}
