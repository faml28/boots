package com.fml.service.impl;

import com.fml.entity.SysUser;
import com.fml.mapper.SysUserMapper;
import com.fml.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by fanml
 * Created by 2020/6/4
 */
@Service
@Slf4j
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserMapper userMapper;

    @Override
    public SysUser selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    @Override
    public SysUser selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }
}
