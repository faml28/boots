package com.fml.controller;

import com.fml.config.shiro.SysPasswordService;
import com.fml.config.util.ShiroUtils;
import com.fml.entity.SysUser;
import com.fml.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String profile(ModelMap modelMap) {
        SysUser user = ShiroUtils.getSysUser();
        modelMap.put("user", user);
        modelMap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        modelMap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
        return prefix + "/profile";
    }

    @GetMapping("checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        SysUser user=ShiroUtils.getSysUser();
        if (passwordService.matches(user, password)) {
            return true;
        }
        return false;
    }
}
