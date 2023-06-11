package com.tobuv.sqtg.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tobuv.sqtg.acl.mapper.RoleMapper;
import com.tobuv.sqtg.acl.service.AdminRoleService;
import com.tobuv.sqtg.acl.service.RoleService;
import com.tobuv.sqtg.model.acl.AdminRole;
import com.tobuv.sqtg.model.acl.Role;
import com.tobuv.sqtg.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> 
		                     implements RoleService {
	@Autowired
	private AdminRoleService adminRoleService;
	//角色分页列表
	@Override
	public IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
		//获取条件值：角色名称
		String roleName = roleQueryVo.getRoleName();
		//创建条件构造器对象
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
		//判断条件值是否为空
		if(!StringUtils.isEmpty(roleName)) {
			//封装条件
			wrapper.like(Role::getRoleName,roleName);
		}
		//调用mapper方法实现条件分页查询
		IPage<Role> pageModel = baseMapper.selectPage(pageParam, wrapper);
		return pageModel;
	}

	/**
	 * 分配角色
	 * @param adminId
	 * @param roleIds
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUserRoleRelationShip(Long adminId, Long[] roleIds) {
		//删除用户分配的角色数据
		adminRoleService.remove(new QueryWrapper<AdminRole>().eq("admin_id", adminId));

		//分配新的角色
		List<AdminRole> userRoleList = new ArrayList<>();
		for(Long roleId : roleIds) {
			if(StringUtils.isEmpty(roleId)) continue;
			AdminRole userRole = new AdminRole();
			userRole.setAdminId(adminId);
			userRole.setRoleId(roleId);
			userRoleList.add(userRole);
		}
		adminRoleService.saveBatch(userRoleList);
	}

	/**
	 * 根据用户获取角色数据
	 * @param adminId
	 * @return
	 */
	@Override
	public Map<String, Object> findRoleByUserId(Long adminId) {
		//查询所有的角色
		List<Role> allRolesList =baseMapper.selectList(null);

		//用户拥有的角色id
		List<AdminRole> existUserRoleList = adminRoleService.list(new QueryWrapper<AdminRole>().eq("admin_id", adminId).select("role_id"));
		List<Long> existRoleList = existUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());

		//对角色进行分类
		List<Role> assignRoles = new ArrayList<Role>();
		for (Role role : allRolesList) {
			//已分配
			if(existRoleList.contains(role.getId())) {
				assignRoles.add(role);
			}
		}

		Map<String, Object> roleMap = new HashMap<>();
		roleMap.put("assignRoles", assignRoles);
		roleMap.put("allRolesList", allRolesList);
		return roleMap;
	}

}