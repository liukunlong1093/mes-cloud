package com.mes.web.controller;
import com.mes.web.pojo.Order;
import com.mes.web.pojo.Result;
import com.mes.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired(required = false)
    private OrderService orderService;

    /**
     * 创建订单
     */
    @GetMapping("/create")
    public Result create(Order order) {
        orderService.create(order);
        return new Result<>("订单创建成功!", 200);
    }
}
