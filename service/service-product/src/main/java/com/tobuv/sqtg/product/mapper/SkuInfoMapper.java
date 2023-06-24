package com.tobuv.sqtg.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tobuv.sqtg.model.product.SkuInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * sku信息 Mapper 接口
 * </p>
 *
 * @author tobuv
 * @since 2023-06-12
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    //解锁库存
    void unlockStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    //验证库存
    SkuInfo checkStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    //锁定库存:update
    Integer lockStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    //遍历集合，得到每个对象，减库存
    void minusStock(@Param("skuId") Long skuId,@Param("skuNum") Integer skuNum);
}
