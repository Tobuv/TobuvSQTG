package com.tobuv.sqtg.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tobuv.sqtg.model.order.OrderInfo;
import com.tobuv.sqtg.vo.order.OrderConfirmVo;
import com.tobuv.sqtg.vo.order.OrderSubmitVo;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author tobuv
 * @since 2023-06-23
 */
public interface OrderInfoService extends IService<OrderInfo> {
    //确认订单
    OrderConfirmVo confirmOrder();

    //生成订单
    Long submitOrder(OrderSubmitVo orderParamVo);
//
//    //订单详情
//    OrderInfo getOrderInfoById(Long orderId);
}
