package com.tobuv.sqtg.order.client;

import com.tobuv.sqtg.model.order.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-order")
public interface OrderFeignClient {

    @GetMapping("/api/order/inner/getOrderInfo/{orderNo}")
    public OrderInfo getOrderInfo(@PathVariable("orderNo") String orderNo);
}
