package com.tobuv.sqtg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tobuv.sqtg.model.product.SkuPoster;
import com.tobuv.sqtg.product.mapper.SkuPosterMapper;
import com.tobuv.sqtg.product.service.SkuPosterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务实现类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
@Service
public class SkuPosterServiceImpl extends ServiceImpl<SkuPosterMapper, SkuPoster> implements SkuPosterService {
    //根据id查询商品海报列表
    @Override
    public List<SkuPoster> getPosterListBySkuId(Long id) {
        LambdaQueryWrapper<SkuPoster> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuPoster::getSkuId,id);
        return baseMapper.selectList(wrapper);
    }
}
