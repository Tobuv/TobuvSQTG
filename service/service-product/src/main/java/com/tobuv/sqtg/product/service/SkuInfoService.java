package com.tobuv.sqtg.product.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tobuv.sqtg.model.product.SkuInfo;
import com.tobuv.sqtg.vo.product.SkuInfoQueryVo;
import com.tobuv.sqtg.vo.product.SkuInfoVo;
import com.tobuv.sqtg.vo.product.SkuStockLockVo;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
public interface SkuInfoService extends IService<SkuInfo> {

    IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    void saveSkuInfo(SkuInfoVo skuInfoVo);

    SkuInfoVo getSkuInfo(Long id);

    void updateSkuInfo(SkuInfoVo skuInfoVo);

    void check(Long skuId, Integer status);

    void publish(Long skuId, Integer status);

    void isNewPerson(Long skuId, Integer status);

//    List<SkuInfo> findSkuInfoList(List<Long> skuIdList);
//
//    List<SkuInfo> findSkuInfoByKeyword(String keyword);
//
//    List<SkuInfo> findNewPersonSkuInfoList();
//
//    SkuInfoVo getSkuInfoVo(Long skuId);
//
//    Boolean checkAndLock(List<SkuStockLockVo> skuStockLockVoList, String orderNo);
}
