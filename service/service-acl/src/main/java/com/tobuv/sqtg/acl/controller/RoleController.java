package com.tobuv.sqtg.acl.controller;

import com.tobuv.sqtg.acl.service.RoleService;
import com.tobuv.sqtg.common.result.Result;
import com.tobuv.sqtg.model.acl.Role;
import com.tobuv.sqtg.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 角色管理
 */
//@CrossOrigin
@RestController
@RequestMapping("/admin/acl/role")
@Api(tags = "角色管理")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "roleQueryVo", value = "查询对象", required = false)
            RoleQueryVo roleQueryVo) {
        Page<Role> pageParam = new Page<>(page, limit);
        IPage<Role> pageModel = roleService.selectPage(pageParam, roleQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.ok(role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public Result save(@RequestBody Role role) {
        boolean is_success = roleService.save(role);
        if(!is_success){
            return Result.fail(null);
        }
        return Result.ok(null);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public Result updateById(@RequestBody Role role) {
        boolean is_success = roleService.updateById(role);
        if(!is_success){
            return Result.fail(null);
        }
        return Result.ok(null);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        boolean is_success = roleService.removeById(id);
        if(!is_success){
            return Result.fail(null);
        }
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        boolean is_success = roleService.removeByIds(idList);
        if(!is_success){
            return Result.fail(null);
        }
        return Result.ok(null);
    }
}