package com.tobuv.sqtg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tobuv.sqtg.model.product.AttrGroup;
import com.tobuv.sqtg.product.mapper.AttrGroupMapper;
import com.tobuv.sqtg.product.service.AttrGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tobuv.sqtg.vo.product.AttrGroupQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {
    //平台属性分组列表
    @Override
    public IPage<AttrGroup> selectPageAttrGroup(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        String name = attrGroupQueryVo.getName();
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(name)) {
            wrapper.like(AttrGroup::getName,name);
        }
        IPage<AttrGroup> pageModel = baseMapper.selectPage(pageParam,wrapper);
        return pageModel;
    }

    //查询所有平台属性分组列表
    @Override
    public List<AttrGroup> findAllListAttrGroup() {
        //LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<AttrGroup> list = baseMapper.selectList(wrapper);
        return list;
    }
}
