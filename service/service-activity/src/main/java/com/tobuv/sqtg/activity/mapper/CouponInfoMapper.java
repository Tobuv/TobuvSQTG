package com.tobuv.sqtg.activity.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tobuv.sqtg.model.activity.CouponInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券信息 Mapper 接口
 * </p>
 *
 * @author tobuv
 * @since 2023-06-14
 */
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

    List<CouponInfo> selectCouponInfoList(@Param("skuId") Long id,
                                          @Param("categoryId") Long categoryId,
                                          @Param("userId") Long userId);

    List<CouponInfo> selectCartCouponInfoList(Long userId);
}
