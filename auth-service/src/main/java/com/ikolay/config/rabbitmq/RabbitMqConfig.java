package com.ikolay.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.auth-exchange}")
    private String exchange;

    @Bean
    DirectExchange exchangeAuth() {
        return new DirectExchange(exchange);
    }

    //Mail işlemleri start:
    @Value("${rabbitmq.mail-bindingKey}")
    private String mailBindingKey;
    @Value("${rabbitmq.mail-queue}")
    private String mailQueueName;

    @Bean
    Queue mailQueue() {
        return new Queue(mailQueueName);
    }

    @Bean
    public Binding bindingMail(final Queue mailQueue,final DirectExchange exchangeAuth){
        return BindingBuilder.bind(mailQueue).to(exchangeAuth).with(mailBindingKey);
    }
    //Mail işlemleri end.

}
