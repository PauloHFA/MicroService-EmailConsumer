package com.ms.email.service;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repository.EmailRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    final EmailRepository emailRepository;
    final JavaMailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel){
        logger.info("Iniciando envio de email para: {}", emailModel.getEmailTo());

        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);
            logger.debug("Email configurado - From: {}, Date: {}", emailFrom, LocalDateTime.now());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());

            logger.info("Enviando email...");
            emailSender.send(message);
            logger.info("Email enviado com sucesso para: {}", emailModel.getEmailTo());

            emailModel.setStatusEmail(StatusEmail.SENT);
            logger.debug("Status do email definido como: SENT");

        } catch (MailException e){
            logger.error("ERRO ao enviar email para {}: {}", emailModel.getEmailTo(), e.getMessage(), e);
            emailModel.setStatusEmail(StatusEmail.ERROR);
            logger.debug("Status do email definido como: ERROR");
        } catch (Exception e) {
            logger.error("ERRO inesperado ao processar email para {}: {}", emailModel.getEmailTo(), e.getMessage(), e);
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }

        try {
            logger.info("Salvando registro do email no banco...");
            EmailModel savedEmail = emailRepository.save(emailModel);
            logger.info("Email salvo no banco com ID: {}", savedEmail.getEmailID());
            return savedEmail;

        } catch (Exception e) {
            logger.error("ERRO ao salvar email no banco: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao salvar registro do email", e);
        }
    }
}