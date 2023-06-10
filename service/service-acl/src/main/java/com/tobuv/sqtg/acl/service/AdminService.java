package com.tobuv.sqtg.acl.service;

import com.tobuv.sqtg.model.acl.Admin;
import com.tobuv.sqtg.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 用户服务接口
 * </p>
 */
public interface AdminService extends IService<Admin> {

	/**
	 * 用户分页列表
	 * @param pageParam
	 * @param userQueryVo
	 * @return
	 */
	IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo userQueryVo);

}