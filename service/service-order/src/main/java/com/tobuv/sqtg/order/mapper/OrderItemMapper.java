package com.tobuv.sqtg.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tobuv.sqtg.model.order.OrderItem;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 订单项信息 Mapper 接口
 * </p>
 *
 * @author tobuv
 * @since 2023-06-23
 */
@Repository
public interface OrderItemMapper extends BaseMapper<OrderItem> {

}
