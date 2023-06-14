package com.tobuv.sqtg.product.api;


import com.tobuv.sqtg.model.product.Category;
import com.tobuv.sqtg.model.product.SkuInfo;
import com.tobuv.sqtg.product.service.CategoryService;
import com.tobuv.sqtg.product.service.SkuInfoService;
import com.tobuv.sqtg.vo.product.SkuInfoVo;
import com.tobuv.sqtg.vo.product.SkuStockLockVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductInnnerController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuInfoService skuInfoService;

    //根据分类id获取分类信息
    @GetMapping("inner/getCategory/{categoryId}")
    public Category  getCategory(@PathVariable Long categoryId) {
        Category category = categoryService.getById(categoryId);
        return category;
    }

    //根据skuid获取sku信息
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId) {
        return skuInfoService.getById(skuId);
    }


}