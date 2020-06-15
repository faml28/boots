package com.fml.controller;

import com.fml.config.ajaxResult.AjaxResult;
import com.fml.config.annotation.Log;
import com.fml.config.enums.BusinessType;
import com.fml.config.shiro.SysPasswordService;
import com.fml.config.util.ShiroUtils;
import com.fml.config.util.StringUtils;
import com.fml.entity.SysUser;
import com.fml.service.ISysUserService;
import org.aspectj.weaver.loadtime.Aj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class SysProfileController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SysProfileController.class);
    private String prefix = "user/profile";

    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysPasswordService passwordService;

    /**
     * 个人信息
     */
    @GetMapping("/profile")
    public String profile(ModelMap modelMap) {
        SysUser user = ShiroUtils.getSysUser();
        modelMap.put("user", user);
        modelMap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        modelMap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
        return prefix + "/profile";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        SysUser user=ShiroUtils.getSysUser();
        if (passwordService.matches(user, password)) {
            return true;
        }
        return false;
    }

    /**
     * 修改密码
     */
    @GetMapping("/resetPwd")
    public String resetPwd(ModelMap mmp){
        SysUser user=ShiroUtils.getSysUser();
        mmp.put("user",userService.selectUserById(user.getUserId()));
        return prefix + "/resetPwd";
    }

    /**
     * 保存修改密码
     */
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(String oldPassword,String newPassword){
        SysUser user=ShiroUtils.getSysUser();
        if (StringUtils.isNotEmpty(newPassword)&& passwordService.matches(user,oldPassword)) {
            user.setSalt(ShiroUtils.randomSalt());
            user.setPassword(passwordService.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
            if (userService.resetUserPwd(user) > 0)
            {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
                return success();
            }
            return error();
        }
        else
        {
            return error("修改密码失败，旧密码错误");
        }


    }
}


