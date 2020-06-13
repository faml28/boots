package com.fml.config.shiro;

import com.fml.entity.SysUser;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;



/**
 * Created by fanml
 * Created by 2020/6/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysLoginServiceTest {
    @Resource
    SysLoginService userservice;


    @Test
    public void login(){
        SysUser user=userservice.login("admin", "1234");
        System.out.println(user);
        }

}