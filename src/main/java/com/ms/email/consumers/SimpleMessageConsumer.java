package com.ms.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SimpleMessageConsumer {

    @RabbitListener(queues = "${broker.queue.email.simple}")
    public void listenSimpleMessage(@Payload String message) {
        System.out.println("Mensagem simples recebida: " + message);
    }
}