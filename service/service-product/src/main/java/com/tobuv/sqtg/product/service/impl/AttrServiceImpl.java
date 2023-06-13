package com.tobuv.sqtg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tobuv.sqtg.model.product.Attr;
import com.tobuv.sqtg.product.mapper.AttrMapper;
import com.tobuv.sqtg.product.service.AttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {
    //根据平台属性分组id查询
    @Override
    public List<Attr> getAttrListByGroupId(Long groupId) {
        LambdaQueryWrapper<Attr> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attr::getAttrGroupId,groupId);
        List<Attr> list = baseMapper.selectList(wrapper);
        return list;
    }
}
