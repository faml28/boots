package com.fml.service;

import com.fml.config.annotation.DataSource;
import com.fml.config.enums.DataSourceType;
import com.fml.entity.User;
import com.fml.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements IUserService {
      @Autowired
      UserMapper userMapper;

      @DataSource(value = DataSourceType.SLAVE)
     public int insert(User user) {

         return userMapper.insert(user);
     }
}
