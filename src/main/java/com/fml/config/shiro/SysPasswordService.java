package com.fml.config.shiro;

import com.fml.config.exception.user.UserException;
import com.fml.config.exception.user.UserPasswordNotMatchException;
import com.fml.entity.SysUser;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;


/**
 * 登录密码方法
 * Created by fanml
 * Created by 2020/6/4
 */
@Component
public class SysPasswordService {
    public void validate(SysUser user, String passwror) {
        String loginName = user.getLoginName();

        //缓存中获取登录次数
        if(!matches(user,passwror)){
            throw new UserPasswordNotMatchException();

        }

    }

    private boolean matches(SysUser user, String passwror) {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), passwror, user.getSalt()));
    }
    //
    public  String encryptPassword(String username,String password,String salt){
        return new Md5Hash(username+password+salt).toHex().toString();

    }
}
