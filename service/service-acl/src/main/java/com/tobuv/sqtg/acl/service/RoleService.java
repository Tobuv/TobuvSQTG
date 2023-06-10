package com.tobuv.sqtg.acl.service;

import com.tobuv.sqtg.model.acl.Role;
import com.tobuv.sqtg.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface RoleService extends IService<Role> {

	//角色分页列表
	IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo);
	/**
	 * 分配角色
	 * @param adminId
	 * @param roleIds
	 */
	void saveUserRoleRealtionShip(Long adminId, Long[] roleIds);

	/**
	 * 根据用户获取角色数据
	 * @param adminId
	 * @return
	 */
	Map<String, Object> findRoleByUserId(Long adminId);

}