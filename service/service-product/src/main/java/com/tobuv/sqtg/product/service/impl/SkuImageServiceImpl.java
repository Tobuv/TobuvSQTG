package com.tobuv.sqtg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tobuv.sqtg.model.product.SkuImage;
import com.tobuv.sqtg.product.mapper.SkuImageMapper;
import com.tobuv.sqtg.product.service.SkuImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品图片 服务实现类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
@Service
public class SkuImageServiceImpl extends ServiceImpl<SkuImageMapper, SkuImage> implements SkuImageService {
    //根据id查询商品图片列表
    @Override
    public List<SkuImage> getImageListBySkuId(Long id) {
        LambdaQueryWrapper<SkuImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuImage::getSkuId,id);
        List<SkuImage> skuImageList = baseMapper.selectList(wrapper);
        return skuImageList;
    }
}
