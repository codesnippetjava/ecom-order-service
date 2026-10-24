package org.codesnippet.ecomorderservice.services;

import org.codesnippet.ecomorderservice.dto.OrderCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AsyncOrderService {
    private final RabbitTemplate rabbitTemplate;

    public AsyncOrderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String placeOrder(Long productId) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(1L, 1L, productId, 1);
        rabbitTemplate.convertAndSend(
                "orders.exchange",
                "order.created",
                orderCreatedEvent
        );
        return "Async Order Placed Successfully";
    }
}
