package com.tobuv.sqtg.acl.service.impl;

import com.tobuv.sqtg.acl.mapper.RoleMapper;
import com.tobuv.sqtg.acl.service.RoleService;
import com.tobuv.sqtg.model.acl.Role;
import com.tobuv.sqtg.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> 
		                     implements RoleService {

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

}