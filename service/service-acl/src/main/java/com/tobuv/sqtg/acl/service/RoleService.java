package com.tobuv.sqtg.acl.service;

import com.tobuv.sqtg.model.acl.Role;
import com.tobuv.sqtg.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RoleService extends IService<Role> {

	//角色分页列表
	IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo);

}