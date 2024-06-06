package me.calebe_oliveira.stock_service.consumer;

import lombok.extern.slf4j.Slf4j;
import me.calebe_oliveira.stock_service.dto.OrderEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {
    @RabbitListener(queues = "${rabbitmq.queue.order.name}")
    public void consume(OrderEvent event) {
        log.info(String.format("Order event received -> %s", event.toString()));

        // save order event data in database
    }
}
