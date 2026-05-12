package com.az.nida.platform.notification.service;

import com.az.nida.platform.notification.dto.EmailDto;

public interface EmailService {

    void sendVerificationEmail(EmailDto dto);

    void sendPasswordResetEmail(EmailDto dto);

    void sendNotificationEmail(EmailDto dto);
}