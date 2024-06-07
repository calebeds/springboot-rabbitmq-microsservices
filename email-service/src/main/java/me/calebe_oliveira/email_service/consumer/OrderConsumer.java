package me.calebe_oliveira.email_service.consumer;

import lombok.extern.slf4j.Slf4j;
import me.calebe_oliveira.email_service.dto.OrderEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    public void consume(OrderEvent orderEvent) {
        log.info(String.format("Order event received in email service -> %s", orderEvent.toString()));

        // email service needs to send an email to customer
    }
}
