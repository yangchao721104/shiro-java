package com.yang.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@MapperScan("com.yang.shiro.mapper")
public class ShiroApplication {
    public static void main( String[] args ) {
        SpringApplication.run(ShiroApplication.class);
        System.out.println( "Hello World!" );
    }
}
