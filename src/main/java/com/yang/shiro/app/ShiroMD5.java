package com.yang.shiro.app;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * @author yang
 * @date 2023/6/1 2:50
 */
public class ShiroMD5 {

    @Test
    public void md5(){
        //明文
        String password = "123";

        //使用MD5加密
        Md5Hash md5Hash = new Md5Hash(password);
        System.out.printf("md5加密= "+md5Hash +"\n");

        //带盐的MD5加密
        Md5Hash salt = new Md5Hash(password, "salt");
        System.out.println("带盐的MD5= " + salt);

        //为了避免破解可以多次迭代加密，保证数据安全
        Md5Hash salt3 = new Md5Hash(password, "salt", 3);
        System.out.println("带盐的MD5三次加密 = " + salt3);

        //使用父类进行加密
        SimpleHash simpleHash3 = new SimpleHash("MD5", password, "salt", 3);
        System.out.println("使用父类进行加密 = " + simpleHash3);
    }
}
