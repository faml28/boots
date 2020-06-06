package com.fml.entity;


import com.fml.config.common.BaseEntity;
import lombok.Data;


/**
 * 部门表 sys_dept
 * 
 * @author ruoyi
 */
@Data
public class SysDept extends BaseEntity
{
    private Long deptId;

    private Long parentId;

    private String ancestors;

    private String deptName;

    private String orderNum;

    private String leader;

    private String phone;

    private String email;

    private String status;

    private String delFlag;

    private String parentName;

}
