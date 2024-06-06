package me.calebe_oliveira.order_service.controller;

import lombok.RequiredArgsConstructor;
import me.calebe_oliveira.order_service.dto.Order;
import me.calebe_oliveira.order_service.dto.OrderEvent;
import me.calebe_oliveira.order_service.publisher.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderProducer orderProducer;

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent event = new OrderEvent();
        event.setStatus("PENDING");
        event.setMessage("Order is in pending status");
        event.setOrder(order);

        orderProducer.sendMessage(event);

        return "Order sent to the RabbitMQ ..";
    }
}
