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