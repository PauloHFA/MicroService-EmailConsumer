package com.ms.email.consumers;

import com.ms.email.dtos.EmailRecordDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
        System.out.println("Received email to: " + emailRecordDto.emailTo());
        System.out.println("Message content: " + emailRecordDto.text());
    }

//    @RabbitListener(queues = "${broker.queue.email.name}")
//    public void debugListener(String rawMessage) {
//        System.out.println("Raw message received: " + rawMessage);
//    }
//
//    @RabbitListener(queues = "${broker.queue.email.name}")
//    public void debugByteListener(byte[] rawMessage) {
//        System.out.println("Mensagem bruta recebida: " + new String(rawMessage));
//    }
//
//    @RabbitListener(queues = "${broker.queue.email.name}")
//    public void listenWithFallback(Object message) {
//        if (message instanceof EmailRecordDto emailRecordDto) {
//            System.out.println("Received email to: " + emailRecordDto.emailTo());
//        } else {
//            System.out.println("Received unknown message: " + message);
//        }
//    }
}