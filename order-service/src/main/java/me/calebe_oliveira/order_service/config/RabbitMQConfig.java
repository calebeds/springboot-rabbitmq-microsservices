package me.calebe_oliveira.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    private final String orderQueue;
    private final String emailQueue;
    private final String exchangeName;
    private final String orderRoutingKey;
    private final String emailRoutingKey;

    public RabbitMQConfig(@Value("${rabbitmq.queue.order.name}") String orderQueue,
                          @Value("${rabbitmq.queue.email.name}") String emailQueue,
                          @Value("${rabbitmq.exchange.name}") String exchangeName,
                          @Value("${rabbitmq.binding.routing.key}") String orderRoutingKey,
                          @Value("${rabbitmq.binding.email.routing.key}") String emailRoutingKey) {
        this.orderQueue = orderQueue;
        this.emailQueue = emailQueue;
        this.exchangeName = exchangeName;
        this.orderRoutingKey = orderRoutingKey;
        this.emailRoutingKey = emailRoutingKey;
    }

    // spring bean for queue - order queue
    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue);
    }


    // spring bean for exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // spring bean for binding between exchange and queue using routing key
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(exchange())
                .with(orderRoutingKey);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(emailRoutingKey);
    }

    // message converter
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    // configure RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
