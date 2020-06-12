package com.fml.controller;

import com.fml.config.ajaxResult.AjaxResult;
import com.fml.config.annotation.Log;
import com.fml.config.enums.BusinessType;
import com.fml.config.exception.UserConstants;
import com.fml.config.page.TableDataInfo;
import com.fml.config.shiro.SysPasswordService;
import com.fml.config.util.ShiroUtils;
import com.fml.controller.BaseController;
import com.fml.entity.SysUser;
import com.fml.service.ISysPostService;
import com.fml.service.ISysRoleService;
import com.fml.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fanml
 * Created by 2020/6/9
 */
@Controller
@RequestMapping("/user")
public class SysUserController extends BaseController {
    private String prefix = "user";
    @Resource
    ISysUserService userService;
    @Resource
    ISysRoleService roleService;
    @Autowired
    ISysPostService postService;
    @Autowired
    SysPasswordService passwordService;


    @RequiresPermissions("user:view")
    @GetMapping()
    public String user() {

        return prefix + "/user";
    }

    @RequiresPermissions("user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 增加用户信息
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.put("roles", roleService.selectRoleAll());
        modelMap.put("posts", postService.selectPostAll());

        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysUser user) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName()))) {
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(userService.insertUser(user));
    }

    /**
     * 檢查用戶名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(SysUser user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 检查邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(SysUser user) {
        return userService.checkEmailUnique(user);
    }

    /**
     * 检查手机号
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysUser user) {
        return userService.checkPhoneUnique(user);
    }

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId,  ModelMap modelMap) {
        modelMap.put("user",userService.selectUserById(userId));
        modelMap.put("role", roleService.selectRolesByUserId(userId));
        modelMap.put("post", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }
}
