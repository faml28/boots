package com.fml.service.impl;

import com.fml.entity.SysRole;
import com.fml.entity.SysUserRole;
import com.fml.mapper.SysRoleMapper;
import com.fml.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by fanml
 * Created by 2020/6/11
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Resource
    SysRoleMapper roleMapper;

    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    @Override
    public Set<String> selectRoleKeys(Long userId) {
        return null;
    }

    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {

        List<SysRole> userRoles = roleMapper.selectRolesByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles)
        {
            for (SysRole userRole : userRoles)
            {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue())
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public List<SysRole> selectRoleAll() {
        return roleMapper.selectRoleList(new SysRole());
    }

    @Override
    public SysRole selectRoleById(Long roleId) {
        return null;
    }

    @Override
    public boolean deleteRoleById(Long roleId) {
        return false;
    }

    @Override
    public int deleteRoleByIds(String ids) throws Exception {
        return 0;
    }

    @Override
    public int insertRole(SysRole role) {
        return 0;
    }

    @Override
    public int updateRole(SysRole role) {
        return 0;
    }

    @Override
    public int authDataScope(SysRole role) {
        return 0;
    }

    @Override
    public String checkRoleNameUnique(SysRole role) {
        return null;
    }

    @Override
    public String checkRoleKeyUnique(SysRole role) {
        return null;
    }

    @Override
    public void checkRoleAllowed(SysRole role) {

    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return 0;
    }

    @Override
    public int changeStatus(SysRole role) {
        return 0;
    }

    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        return 0;
    }

    @Override
    public int deleteAuthUsers(Long roleId, String userIds) {
        return 0;
    }

    @Override
    public int insertAuthUsers(Long roleId, String userIds) {
        return 0;
    }
}
