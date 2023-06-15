package com.tobuv.sqtg.acl.controller;

import com.tobuv.sqtg.acl.service.PermissionService;
import com.tobuv.sqtg.acl.service.RoleService;
import com.tobuv.sqtg.common.result.Result;
import com.tobuv.sqtg.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "菜单管理")
//@CrossOrigin //跨域
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    //查询所有菜单
    //    url: `${api_name}`,
    //    method: 'get'
    @ApiOperation("查询所有菜单")
    @GetMapping
    public Result list() {
        List<Permission> list = permissionService.queryAllPermission();
        return Result.ok(list);
    }

    //添加菜单
    //    url: `${api_name}/save`,
    //    method: "post",
    //    data: permission
    @ApiOperation("添加菜单")
    @PostMapping("save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    //修改菜单
    //    url: `${api_name}/update`,
    //    method: "put",
    //    data: permission
    @ApiOperation("修改菜单")
    @PutMapping("update")
    public Result update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }

    //递归删除菜单
    //    url: `${api_name}/remove/${id}`,
    //    method: "delete"
    @ApiOperation("递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }

    //更具角色获取权限数据
    //    url: `${api_name}/toAssign/${roleId}`,
    //    method: 'get'
    @ApiOperation("获取角色权限")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        List<Permission> permissionMap = permissionService.findPermissionByRoleId(roleId);
        return Result.ok(permissionMap);
    }

    //    url: `${api_name}/doAssign`,
    //    method: "post",
    //    params: {roleId, permissionId}
    @ApiOperation(value = "根据角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam Long roleId,@RequestParam Long[] permissionId) {
        permissionService.saveRolePermissionRelationShip(roleId,permissionId);
        return Result.ok(null);
    }
}
