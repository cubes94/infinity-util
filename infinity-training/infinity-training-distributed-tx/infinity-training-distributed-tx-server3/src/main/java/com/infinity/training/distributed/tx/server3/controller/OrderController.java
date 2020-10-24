package com.infinity.training.distributed.tx.server3.controller;

import com.infinity.training.distributed.tx.server3.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/23 18:29
 */
@RestController
@RequestMapping("/server3")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping(value = "/addOrder")
    public Integer addOrder(@RequestParam(value = "orderDesc", required = false) String orderDesc) {
        return orderService.addOrder(orderDesc);
    }
}
