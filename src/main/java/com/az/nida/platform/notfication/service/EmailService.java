package com.az.nida.platform.notfication.service;

import com.az.nida.platform.notfication.dto.EmailDto;

public interface EmailService {

    void sendVerificationEmail(EmailDto dto);

    void sendPasswordResetEmail(EmailDto dto);

    void sendNotificationEmail(EmailDto dto);
}