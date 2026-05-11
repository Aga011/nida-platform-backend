package com.az.notfication.service;

import com.az.notfication.dto.EmailDto;

public interface EmailService {

    void sendVerificationEmail(EmailDto dto);

    void sendPasswordResetEmail(EmailDto dto);

    void sendNotificationEmail(EmailDto dto);
}