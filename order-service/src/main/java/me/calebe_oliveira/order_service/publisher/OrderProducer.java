package me.calebe_oliveira.order_service.publisher;

import lombok.extern.slf4j.Slf4j;
import me.calebe_oliveira.order_service.dto.OrderEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProducer {
    private final String exchangeName;
    private final String orderRoutingKey;
    private final String emailRoutingKey;
    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(@Value("${rabbitmq.exchange.name}") String exchangeName,
                         @Value("${rabbitmq.binding.routing.key}") String orderRoutingKey,
                         @Value("${rabbitmq.binding.email.routing.key}") String emailRoutingKey,
                         RabbitTemplate rabbitTemplate) {
        this.exchangeName = exchangeName;
        this.orderRoutingKey = orderRoutingKey;
        this.emailRoutingKey = emailRoutingKey;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderEvent orderEvent) {
        log.info(String.format("Order event sent to RabbitMQ -> %s", orderEvent.toString()));

        // send an order event to order queue
        rabbitTemplate.convertAndSend(exchangeName, orderRoutingKey, orderEvent);

        // send an order event to email queue
        rabbitTemplate.convertAndSend(exchangeName, emailRoutingKey, orderEvent);
    }
}
