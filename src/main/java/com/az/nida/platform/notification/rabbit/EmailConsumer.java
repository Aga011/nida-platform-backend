package com.az.nida.platform.notification.rabbit;

import com.az.nida.platform.notification.dto.EmailDto;
import com.az.nida.platform.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.az.nida.platform.config.RabbitMQConfig.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = EMAIL_VERIFY_QUEUE)
    public void consumeVerificationEmail(EmailDto dto) {
        log.info("Verification email consume edildi: {}", dto.to());
        emailService.sendVerificationEmail(dto);
    }

    @RabbitListener(queues = EMAIL_RESET_QUEUE)
    public void consumePasswordResetEmail(EmailDto dto) {
        log.info("Password reset email consume edildi: {}", dto.to());
        emailService.sendPasswordResetEmail(dto);
    }

    @RabbitListener(queues = EMAIL_NOTIFY_QUEUE)
    public void consumeNotificationEmail(EmailDto dto) {
        log.info("Notification email consume edildi: {}", dto.to());
        emailService.sendNotificationEmail(dto);
    }
}