package com.tobuv.sqtg.activity.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tobuv.sqtg.model.activity.ActivityInfo;
import com.tobuv.sqtg.model.activity.ActivityRule;
import com.tobuv.sqtg.model.activity.ActivitySku;
import feign.Param;

import java.util.List;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author tobuv
 * @since 2023-06-14
 */
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {
    // 如果之前参加过，活动正在进行中，排除商品
    List<Long> selectSkuIdListExist(@Param("skuIdList") List<Long> skuIdList);

    //根据skuId进行查询，查询sku对应活动里面规则列表
    List<ActivityRule> findActivityRule(Long skuId);

    //根据所有skuId列表获取参与活动
    List<ActivitySku> selectCartActivity(@Param("skuIdList") List<Long> skuIdList);
}
