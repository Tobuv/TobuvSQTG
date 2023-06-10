package com.tobuv.sqtg.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tobuv.sqtg.acl.mapper.AdminRoleMapper;
import com.tobuv.sqtg.acl.service.AdminRoleService;
import com.tobuv.sqtg.model.acl.AdminRole;
import org.springframework.stereotype.Service;


/**
 * 用户角色服务实现类
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole>
										implements AdminRoleService {

}