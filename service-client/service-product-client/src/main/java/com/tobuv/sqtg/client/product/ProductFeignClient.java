package com.tobuv.sqtg.client.product;

import com.tobuv.sqtg.model.product.Category;
import com.tobuv.sqtg.model.product.SkuInfo;
import com.tobuv.sqtg.vo.product.SkuInfoVo;
import com.tobuv.sqtg.vo.product.SkuStockLockVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-product")
public interface ProductFeignClient {

    @GetMapping("/api/product/inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId);

//    //所有分类
//    @GetMapping("/api/product/inner/findAllCategoryList")
//    public List<Category> findAllCategoryList();
//
//    @GetMapping("/api/product/inner/getCategory/{categoryId}")
//    public Category getCategory(@PathVariable("categoryId") Long categoryId);
//
//    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
//    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId);
//
    //根据skuId列表得到sku信息列表
    @PostMapping("/api/product/inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList);

    //根据分类id获取分类列表
    @PostMapping("/api/product/inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryIdList);
//
    //根据关键字匹配sku列表
    @GetMapping("/api/product/inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword);

//    //验证和锁定库存
//    @PostMapping("/api/product/inner/checkAndLock/{orderNo}")
//    public Boolean checkAndLock(@RequestBody List<SkuStockLockVo> skuStockLockVoList,
//                                @PathVariable("orderNo") String orderNo);
}
