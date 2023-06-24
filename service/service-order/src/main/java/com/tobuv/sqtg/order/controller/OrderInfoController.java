package com.tobuv.sqtg.order.controller;


import com.tobuv.sqtg.common.result.Result;
import com.tobuv.sqtg.model.order.OrderInfo;
import com.tobuv.sqtg.order.service.OrderInfoService;
import com.tobuv.sqtg.vo.order.OrderConfirmVo;
import com.tobuv.sqtg.vo.order.OrderSubmitVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author tobuv
 * @since 2023-06-23
 */
@RestController
@RequestMapping(value="/api/order")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation("确认订单")
    @GetMapping("auth/confirmOrder")
    public Result confirm() {
        OrderConfirmVo orderConfirmVo = orderInfoService.confirmOrder();
        return Result.ok(orderConfirmVo);
    }

    @ApiOperation("生成订单")
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderSubmitVo orderParamVo) {
        Long orderId = orderInfoService.submitOrder(orderParamVo);
        return Result.ok(orderId);
    }
//
//    @ApiOperation("获取订单详情")
//    @GetMapping("auth/getOrderInfoById/{orderId}")
//    public Result getOrderInfoById(@PathVariable("orderId") Long orderId){
//        OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
//        return Result.ok(orderInfo);
//    }
}

