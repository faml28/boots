package com.fml.entity;


import com.fml.config.common.BaseEntity;
import lombok.Data;

/**
 * 角色表 sys_role
 * 
 * @author ruoyi
 */
@Data
public class SysRole extends BaseEntity
{
    private Long roleId;

    private String roleName;

    private String roleKey;

    private String roleSort;

    private String dataScope;

    private String status;

    private String delFlag;

    /** 用户是否存在次角色标识 默认不存在*/
    private boolean flag = false;

    private Long[] menuIds;


}
