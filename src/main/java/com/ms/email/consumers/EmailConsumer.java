package com.ms.email.consumers;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.models.EmailModel;
import com.ms.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService){
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
        try {
            logger.info("📩 Mensagem recebida da fila: {}", emailRecordDto);

            var emailModel = new EmailModel();
            BeanUtils.copyProperties(emailRecordDto, emailModel);

            emailService.sendEmail(emailModel);

            logger.info("✅ Email enviado com sucesso para: {}", emailModel.getEmailTo());
        } catch (Exception e) {
            logger.error("❌ Erro ao processar mensagem da fila: {}", emailRecordDto, e);
        }
    }
}