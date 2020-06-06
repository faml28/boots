package com.fml.controller;

import com.fml.entity.SysMenu;
import com.fml.entity.SysUser;
import com.fml.service.ISysMenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by fanml
 * Created by 2020/6/5
 */
@Controller
public class SysIndexController extends BaseController{

    @Resource
    private ISysMenuService menuService;

    @GetMapping("/index")
    public String index(ModelMap mmap){
        //获取身份信息
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();;
        //根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);

        return "index";
    }
}
