# 社区团购项目
## 拓展功能
### 开发为角色分配菜单（权限）接口
- 接口1：查询所有菜单 和 查询该角色已经分配过的菜单
    - 参数：角色编号roleId
- 接口2：分配菜单接口
    - 参数：角色编号roleId，待分配菜单编号permissionIds

### 添加角色菜单Service和Mapper

#### (1)添加RolePermissionService

```java
package com.tobuv.sqtg.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tobuv.sqtg.model.acl.RolePermission;

/**
 * <p>
 * 角色權限服务接口
 * </p>
 */
public interface RolePermissionService extends IService<RolePermission> {

}
```

#### (2)添加RolePermissionServiceImpl

```java
package com.tobuv.sqtg.acl.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tobuv.sqtg.acl.mapper.RolePermissionMapper;
import com.tobuv.sqtg.acl.service.RolePermissionService;
import com.tobuv.sqtg.model.acl.RolePermission;
import org.springframework.stereotype.Service;

/**
 * 角色权限服务实现类
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
		implements RolePermissionService{
}
```

#### (3)添加RolePermissionMapper

```java
package com.tobuv.sqtg.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tobuv.sqtg.model.acl.RolePermission;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色权限Mpper接口
 * </p>
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}
```

### 添加PermissionController方法

> 分配菜单这部分前端代码在`ssyx-admin\src\api\acl\permission.js`里所以在`PermissionController`中处理这部分逻辑

```java
    //更具角色获取权限数据
    //    url: `${api_name}/toAssign/${roleId}`,
    //    method: 'get'
    @ApiOperation("获取角色权限")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        List<Permission> permissionMap = 
            permissionService.findPermissionByRoleId(roleId);
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
```

### PermissionService接口

```java
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
```

### PermissionServiceImpl方法

这里需要注意的是Permission类中有个字段是isSelect用来表示该菜单是否被选中，用于在前端的复选框中显示。

所以在`findPermissionByRoleId`方法中需要将根据roleId查询到的Permission的isSelect赋值为true。

```java
    //根据用户查询权限
    @Override
    public List<Permission> findPermissionByRoleId(Long roleId) {
        //查询所有权限
        List<Permission> allPermissionList =
                baseMapper.selectList(null);
        //根据roleId查询对应的权限
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        List<RolePermission> rolePermissionList = rolePermissionService.list(wrapper);

        List<Long> permissionIdList =
                rolePermissionList.stream()
                        .map(item -> item.getPermissionId())
                        .collect(Collectors.toList());
        for (Permission p : allPermissionList) {
            if(permissionIdList.contains(p.getId())){
                p.setSelect(true);//前端复选框显示
            }
        }

        List<Permission> allPermissions = 
            PermissionHelper.buildPermission(allPermissionList);
        return allPermissions;
    }

    //分配权限
    @Override
    public void saveRolePermissionRelationShip(Long roleId, Long[] permissionIds) {
        //1 删除roleId原有的权限
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        rolePermissionService.remove(wrapper);
        //2 重新分配
        List<RolePermission> list = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permissionId);
            rolePermission.setRoleId(roleId);
            list.add(rolePermission);
        }
        rolePermissionService.saveBatch(list);
    }
```

### swagger测试

![image-20230611235211716](https://pic-typora-nb.oss-cn-hangzhou.aliyuncs.com/img/image-20230611235211716.png)

### 前端测试

![image-20230611235312172](C:\Users\chengsongren\AppData\Roaming\Typora\typora-user-images\image-20230611235312172.png)
