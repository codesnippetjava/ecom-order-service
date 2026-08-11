package org.codesnippet.ecomorderservice.controller;

import org.codesnippet.ecomorderservice.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{productId}")
    public String placeOrder(@PathVariable Long productId) throws ExecutionException, InterruptedException {
       return orderService.placeOrder(productId);
    }
}
