package com.fml.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fanml
 * Created by 2020/6/11
 */
@Controller
@RequestMapping("/role")
public class SysRoleController extends BaseController{
    private String prefix = "role";

    @RequiresPermissions("role:view")
    @GetMapping()
    public String role() {
        return prefix+"/role";
    }



}
