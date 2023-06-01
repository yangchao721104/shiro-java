package com.yang.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yang.shiro.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.IOException;
import java.io.InputStream;

/**
 * @author yang
 * @date 2023/6/1 4:42
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private MyRealm myRealm;

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        //创建defaultWebSecurityManager 对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //创建加密对象 设置相关属性
        //采用MD5加密，迭代加密次数
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(3);
        //将加密对象存储到myRealm中
        myRealm.setCredentialsMatcher(matcher);
        //将myRealm存入defaultWebSecurityManager 对象
        defaultWebSecurityManager.setRealm(myRealm);
        //设置remember
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());

        //设置缓存管理器
        defaultWebSecurityManager.setCacheManager(getEhCacheManager());
        //返回
        return defaultWebSecurityManager;
    }

    //缓存管理器
    public CacheManager getEhCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        //获取编译目录下的资源流对象
        InputStream input = null;
        try {
            input = ResourceUtils.getInputStreamForPath("classpath:ehcache/ehcache-shiro.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取ehcache的缓存管理对象
        net.sf.ehcache.CacheManager cacheManager = new net.sf.ehcache.CacheManager(input);
        //获取缓存对象
        ehCacheManager.setCacheManager(cacheManager);

        return ehCacheManager;
    }

    //配置shiro内置过滤器拦截范围
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        //设置不认证可以访问的资源
        definition.addPathDefinition("/myController/userLogin","anon");
        definition.addPathDefinition("/login","anon");
        //设置登出过滤器
        definition.addPathDefinition("/logout","logout");
        //设置需要进行登录认证的拦截范围
        definition.addPathDefinition("/**","authc");
        //添加存在用户的过滤器
        definition.addPathDefinition("/**","user");
        return definition;
    }

    //创建shiro的cookie管理对象

    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        rememberMeManager.setCipherKey("1234567890987654".getBytes());
        return rememberMeManager;
    }

    //cookie属性设置
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //设置跨域
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*60);
        return cookie;
    }

    //shiro标签配置
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


}
