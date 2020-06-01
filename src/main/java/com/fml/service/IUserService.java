package com.fml.service;

import com.fml.config.annotation.DataSource;
import com.fml.config.enums.DataSourceType;
import com.fml.entity.User;

import org.springframework.stereotype.Service;

@Service

public interface   IUserService {
     @DataSource(value = DataSourceType.SLAVE)
     public int insert(User user) ;
}
