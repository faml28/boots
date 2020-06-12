package com.fml.controller;



import com.fml.entity.SysDept;
import com.fml.entity.Ztree;
import com.fml.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String dept()
    {
        return prefix + "/dept";
    }

    @RequiresPermissions("dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysDept> list(SysDept dept)
    {
        List<SysDept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = deptService.selectDeptTree(new SysDept());
        return ztrees;
    }
    /**
     * 归属部门
     */
    @GetMapping(value = { "/selectDeptTree/{deptId}", "/selectDeptTree/{deptId}/{excludeId}" })
    public String selectDeptTree(@PathVariable("deptId") Long deptId,@PathVariable(value = "excludeId",
            required = false) String excludeId, ModelMap mmap){
        mmap.put("dept", deptService.selectDeptById(deptId));
        mmap.put("excludeId",excludeId);
        return prefix + "/tree";

    }


}
