package com.fml.mapper;

import com.fml.entity.SysUser;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * Created by fanml
 * Created by 2020/6/3
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SysUserMapperTest {
    @Autowired
    SysUserMapper sysUserMapper;
    @Test
    void selectUserByLoginName() {
        SysUser user=sysUserMapper.selectUserByLoginName("admin");
        System.out.println(user);
    }

    @Test
    void selectUserByPhoneNumber() {



    }
}