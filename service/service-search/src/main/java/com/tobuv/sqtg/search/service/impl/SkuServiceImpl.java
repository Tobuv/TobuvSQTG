package com.tobuv.sqtg.search.service.impl;


import com.tobuv.sqtg.client.product.ProductFeignClient;
import com.tobuv.sqtg.enums.SkuType;
import com.tobuv.sqtg.model.product.Category;
import com.tobuv.sqtg.model.product.SkuInfo;
import com.tobuv.sqtg.model.search.SkuEs;
import com.tobuv.sqtg.search.repository.SkuRepository;
import com.tobuv.sqtg.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private ProductFeignClient productFeignClient;
    //上架
    @Override
    public void upperSku(Long skuId) {
        //1 通过远程调用 ，根据skuid获取相关信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if(skuInfo == null) {
            return;
        }
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());
        //2 获取数据封装SkuEs对象
        SkuEs skuEs = new SkuEs();
        //封装分类
        if(category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        //封装sku信息部分
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(skuInfo.getSkuType() == SkuType.COMMON.getCode()) {//普通商品
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        }
        //3 调用方法添加ES
        skuRepository.save(skuEs);
    }

    //下架
    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }

}
