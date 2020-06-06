package com.fml.service;

import com.fml.entity.SysMenu;
import com.fml.entity.SysUser;

import java.util.List;

/**
 * Created by fanml
 * Created by 2020/6/5
 */
public interface ISysMenuService {
    /**
     * 根据用户ID查询菜单
     * @param user 用户信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenusByUser(SysUser user);
}
