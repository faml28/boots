package com.fml.service.impl;

import com.fml.entity.SysMenu;
import com.fml.entity.SysUser;
import com.fml.mapper.SysMenuMapper;
import com.fml.service.ISysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fanml
 * Created by 2020/6/5
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {
    @Resource
    private SysMenuMapper menuMapper;

    @Override
    public List<SysMenu> selectMenusByUser(SysUser user) {
        List<SysMenu> menuList = new LinkedList<>();
        if(user.isAdmin()){
            menuList=menuMapper.selectMenuNormalAll();

        }else {
            menuList=menuMapper.selectMenusByUserId(user.getUserId());
        }
        return getChildPerms(menuList, 0);
    }
    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu menu : list) {
            if (menu.getParentId() == parentId) {
                recursionFn(list, menu);
                returnList.add(menu);
            }
        }
        return returnList;

    }
    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext()) {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }
    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }


}
