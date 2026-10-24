package org.codesnippet.ecomorderservice.controller;

import org.codesnippet.ecomorderservice.services.AsyncOrderService;
import org.codesnippet.ecomorderservice.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final AsyncOrderService asyncOrderService;

    public OrderController(OrderService orderService, AsyncOrderService asyncOrderService) {
        this.orderService = orderService;
        this.asyncOrderService = asyncOrderService;
    }

    @PostMapping("/{productId}")
    public String placeOrder(@PathVariable Long productId) throws ExecutionException, InterruptedException {
       return orderService.placeOrder(productId);
    }

    @PostMapping("async/{productId}")
    public String placeOrderAsync(@PathVariable Long productId) throws ExecutionException, InterruptedException {
        return asyncOrderService.placeOrder(productId);
    }
}
