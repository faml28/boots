package com.fml.entity;

import com.fml.config.common.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUser extends BaseEntity {
    private Long userId;

    private Long deptId;

    private Long parentId;

    private Long roleId;

    private String loginName;

    private String userName;

    private String email;

    private String phonenumber;

    private String sex;

    private String avatar;

    private String password;

    private String salt;

    private String status;

    private String delFlag;

    private String loginIp;

    private Date loginDate;

    private SysDept dept;

    private List<SysRole> roles;

    private Long[] roleIds;

    private Long[] postIds;

    public boolean isAdmin(){
        return isAdmin(this.userId);
    }
    public static boolean isAdmin(Long userId){
        return userId!=null && 1L==userId;
    }

}
