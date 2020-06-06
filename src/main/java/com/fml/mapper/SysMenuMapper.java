package com.fml.mapper;

import com.fml.entity.SysMenu;

import java.util.List;

/**
 * Created by fanml
 * Created by 2020/6/5
 */
public interface SysMenuMapper {
    /**
     * 查询系统正常显示菜单（不含按钮）
     *
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuNormalAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenusByUserId(Long userId);

}
