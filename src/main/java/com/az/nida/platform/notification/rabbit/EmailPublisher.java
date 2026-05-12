package com.az.nida.platform.notification.rabbit;

import com.az.nida.platform.config.RabbitMQConfig;
import com.az.nida.platform.notification.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendVerificationEmail(String to, String fullName, String token) {
        EmailDto dto = new EmailDto(
                to,
                fullName,
                "NIDA — Email Təsdiqləmə",
                "Emailinizi təsdiqləmək üçün linki izləyin",
                token
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMAIL_EXCHANGE,
                RabbitMQConfig.EMAIL_VERIFY_KEY,
                dto
        );
        log.info("Verification email queue-ya göndərildi: {}", to);
    }

    public void sendPasswordResetEmail(String to, String fullName, String token) {
        EmailDto dto = new EmailDto(
                to,
                fullName,
                "NIDA — Şifrə Sıfırlama",
                "Şifrənizi sıfırlamaq üçün linki izləyin",
                token
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMAIL_EXCHANGE,
                RabbitMQConfig.EMAIL_RESET_KEY,
                dto
        );
        log.info("Password reset email queue-ya göndərildi: {}", to);
    }

    public void sendNotificationEmail(String to, String fullName, String subject, String body) {
        EmailDto dto = new EmailDto(
                to,
                fullName,
                subject,
                body,
                null
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMAIL_EXCHANGE,
                RabbitMQConfig.EMAIL_NOTIFY_KEY,
                dto
        );
        log.info("Notification email queue-ya göndərildi: {}", to);
    }
}