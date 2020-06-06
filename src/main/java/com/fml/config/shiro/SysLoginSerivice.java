package com.fml.config.shiro;


import com.fml.config.enums.UserStatus;
import com.fml.config.exception.UserConstants;
import com.fml.config.exception.user.CaptchaException;
import com.fml.config.exception.user.UserBlockedException;
import com.fml.config.exception.user.UserNotExistsException;
import com.fml.config.exception.user.UserPasswordNotMatchException;
import com.fml.config.util.ServletUtils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.fml.entity.SysUser;
import com.fml.service.ISysUserService;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;


/**
 * Created by fanml
 * Created by 2020/6/4
 */
@Component
public class SysLoginSerivice {
    @Resource
    ISysUserService iSysUserService;
    @Resource
    SysPasswordService sysPasswordService;

    //验证码校验
    public SysUser login(String login, String password) {
       /* if (StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            throw new CaptchaException();
        }*/

        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            throw new UserNotExistsException();
        }
        //密码不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new UserPasswordNotMatchException();
        }
        //查询用户信息
        SysUser sysUser = iSysUserService.selectUserByLoginName(login);
        if (sysUser == null && maybeMobilePhoneNumber(login)) {
            sysUser = iSysUserService.selectUserByLoginName(login);
        }
        if (sysUser == null && maybeEmail(login)) {

            sysUser = iSysUserService.selectUserByLoginName(login);
        }
        if (sysUser == null) {
            throw new UserNotExistsException();
        }
        if (UserStatus.DELETED.getCode().equals(sysUser.getDelFlag())) {
            throw new UserBlockedException();
        }

        //校验密码是否正确
        sysPasswordService.validate(sysUser, password);

        return sysUser;


    }
    private boolean maybeMobilePhoneNumber(String name){
        if(!name.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN)){
            return false;
        }
        return true;
    }

    private boolean maybeEmail(String email) {
        if (!email.matches(UserConstants.EMAIL_PATTERN)) {
            return false;
        }
        return true;
    }
}
