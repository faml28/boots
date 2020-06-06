package com.fml.config.shiro;


import com.fml.config.exception.user.*;
import com.fml.entity.SysUser;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义Realm 处理登录 权限
 * 
 * @author ruoyi
 */
public class UserRealm extends AuthorizingRealm
{


    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    private SysLoginSerivice sysLoginSerivice;


    /*
    * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("登录认证代码。。。。");
        UsernamePasswordToken upToken=(UsernamePasswordToken)authenticationToken;
        String username=upToken.getUsername();
        String passwrod="";
        if (upToken.getPassword() != null) {
            passwrod = new String(upToken.getPassword());

        }
        SysUser user=null;
        try{
        user = sysLoginSerivice.login(username, passwrod);
        } catch (CaptchaException e){
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e){
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e){
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserBlockedException e){
            throw new LockedAccountException(e.getMessage(), e);
        } catch (RoleBlockedException e){
            throw new LockedAccountException(e.getMessage(), e);
        } catch(Exception e) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, passwrod, getName());
        return info;

    }
}
