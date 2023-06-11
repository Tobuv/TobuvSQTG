package com.tobuv.sqtg.acl.service;

import com.tobuv.sqtg.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface PermissionService extends IService<Permission> {

    //查询所有菜单
    List<Permission> queryAllPermission();

    //递归删除菜单
    void removeChildById(Long id);

    /**
     * 根据角色id查询权限
     * @param roleId
     * @return
     */
    List<Permission> findPermissionByRoleId(Long roleId);

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionIds
     */
    void saveRolePermissionRelationShip(Long roleId, Long[] permissionIds);
}
