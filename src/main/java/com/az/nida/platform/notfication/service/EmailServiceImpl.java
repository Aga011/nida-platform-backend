package com.az.nida.platform.notfication.service;

import com.az.nida.platform.notfication.dto.EmailDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    public void sendVerificationEmail(EmailDto dto) {
        String verifyLink = frontendUrl + "/verify?token=" + dto.token();
        String html = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #00C9A7;">NIDA Platformasına Xoş Gəldiniz!</h2>
                    <p>Salam, <strong>%s</strong>!</p>
                    <p>Emailinizi təsdiqləmək üçün aşağıdakı düyməyə klikləyin:</p>
                    <a href="%s" style="
                        display: inline-block;
                        padding: 12px 24px;
                        background-color: #00C9A7;
                        color: white;
                        text-decoration: none;
                        border-radius: 8px;
                        font-weight: bold;
                        margin: 16px 0;
                    ">Emaili Təsdiqlə</a>
                    <p style="color: #666;">Bu link 24 saat ərzində etibarlıdır.</p>
                    <p style="color: #666;">Əgər siz bu hesabı yaratmamısınızsa, bu emaili nəzərə almayın.</p>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 24px 0;">
                    <p style="color: #999; font-size: 12px;">NIDA Learning Platform</p>
                </div>
                """.formatted(dto.fullName(), verifyLink);

        sendHtmlEmail(dto.to(), dto.subject(), html);
    }

    @Override
    public void sendPasswordResetEmail(EmailDto dto) {
        String resetLink = frontendUrl + "/reset-password?token=" + dto.token();
        String html = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #4F87FF;">Şifrə Sıfırlama</h2>
                    <p>Salam, <strong>%s</strong>!</p>
                    <p>Şifrənizi sıfırlamaq üçün aşağıdakı düyməyə klikləyin:</p>
                    <a href="%s" style="
                        display: inline-block;
                        padding: 12px 24px;
                        background-color: #4F87FF;
                        color: white;
                        text-decoration: none;
                        border-radius: 8px;
                        font-weight: bold;
                        margin: 16px 0;
                    ">Şifrəni Sıfırla</a>
                    <p style="color: #666;">Bu link 1 saat ərzində etibarlıdır.</p>
                    <p style="color: #666;">Əgər siz bu sorğunu göndərməmisinizsə, bu emaili nəzərə almayın.</p>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 24px 0;">
                    <p style="color: #999; font-size: 12px;">NIDA Learning Platform</p>
                </div>
                """.formatted(dto.fullName(), resetLink);

        sendHtmlEmail(dto.to(), dto.subject(), html);
    }

    @Override
    public void sendNotificationEmail(EmailDto dto) {
        String html = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #00C9A7;">NIDA Bildiriş</h2>
                    <p>Salam, <strong>%s</strong>!</p>
                    <p>%s</p>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 24px 0;">
                    <p style="color: #999; font-size: 12px;">NIDA Learning Platform</p>
                </div>
                """.formatted(dto.fullName(), dto.body());

        sendHtmlEmail(dto.to(), dto.subject(), html);
    }

    private void sendHtmlEmail(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(message);
            log.info("Email göndərildi: {}", to);
        } catch (Exception e) {
            log.error("Email göndərilmədi: {} — {}", to, e.getMessage());
        }
    }
}