package com.fml.controller;



import com.fml.config.ajaxResult.AjaxResult;
import com.fml.config.annotation.Log;
import com.fml.config.enums.BusinessType;
import com.fml.config.exception.UserConstants;
import com.fml.config.util.ShiroUtils;
import com.fml.config.util.StringUtils;
import com.fml.entity.SysDept;
import com.fml.entity.SysRole;
import com.fml.entity.Ztree;
import com.fml.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by fanml
 * Created by 2020/6/10
 */
@Controller
@RequestMapping("/dept")
public class SysDeptController extends BaseController {
    private String prefix = "dept";

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("dept:view")
    @GetMapping()
    public String dept() {

        return prefix + "/dept";
    }

    @RequiresPermissions("dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysDept> list(SysDept dept) {
        List<SysDept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = deptService.selectDeptTree(new SysDept());
        return ztrees;
    }
    /**
     * 加载部门列表树（排除下级）
     */
    @GetMapping("/treeData/{excludeId}")
    @ResponseBody
    public List<Ztree> treeDataExcludeChild(@PathVariable(value = "excludeId", required = false) Long excludeId)
    {
        SysDept dept = new SysDept();
        dept.setDeptId(excludeId);
        List<Ztree> ztrees = deptService.selectDeptTreeExcludeChild(dept);
        return ztrees;
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData(SysRole role)
    {
        List<Ztree> ztrees = deptService.roleDeptTreeData(role);
        return ztrees;
    }

    /**
     * 归属部门
     */
    @GetMapping(value = {"/selectDeptTree/{deptId}", "/selectDeptTree/{deptId}/{excludeId}"})
    public String selectDeptTree(@PathVariable("deptId") Long deptId, @PathVariable(value = "excludeId",
            required = false) String excludeId, ModelMap mmap) {
        mmap.put("dept", deptService.selectDeptById(deptId));
        mmap.put("excludeId", excludeId);
        return prefix + "/tree";

    }


    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") long parentId, ModelMap mmp) {
        mmp.put("dept", deptService.selectDeptById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存
     */
    @RequestMapping("/add")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("dept:add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDept dept) {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return error("新增部'" + dept.getDeptName() + "'失败，部门名称已经存在");
        }
        dept.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(deptService.insertDept(dept));

    }

    /**
     * 修改
     */
    @RequestMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") long deptId, ModelMap mmp) {
        SysDept dept = deptService.selectDeptById(deptId);
        if (StringUtils.isNotNull(dept) && 100l == deptId) {
            dept.setParentName("无");
        }
        mmp.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 修改保存
     */
    @RequiresPermissions("dept:edit")
    @RequestMapping("/edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @ResponseBody
    public AjaxResult editSave(@Validated SysDept dept) {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return error("修改用户部门'" + dept.getDeptName() + "'失败，部门名称已经存在");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return error("修改上级用户部门'" + dept.getDeptName() + "'失败，部门名称已经存在");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
                && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
            return AjaxResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除
     */
    @RequiresPermissions("dept:remove")
    @RequestMapping("/remove/{deptId}")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") long deptId) {
        if (deptService.selectDeptCount(deptId) > 0) {
            return AjaxResult.warn("存在下级部门，不能删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.warn("部门存在用户，不能删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }

}


