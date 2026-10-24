package org.codesnippet.ecomorderservice.client.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "orders.exchange";
    public static final String QUEUE = "inventory.queue";
    public static final String ROUTING_KEY = "order.created";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue inventoryQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding inventoryBinding(Queue inventoryQueue, DirectExchange directExchange) {
     return BindingBuilder
             .bind(inventoryQueue)
             .to(directExchange)
             .with(ROUTING_KEY);
    }
    
    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
