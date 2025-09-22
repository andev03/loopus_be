package com.loopus.loopus_be.service;

import com.loopus.loopus_be.service.IService.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Your OTP Code - Loopus App");

            String htmlContent = buildOtpTemplate(otp);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Could not send OTP email", e);
        }
    }

    private String buildOtpTemplate(String otp) {
        return """
        <html>
          <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
              <h2 style="text-align: center; color: #4CAF50;">Loopus Verification</h2>
              <p style="font-size: 16px; color: #333;">Hello,</p>
              <p style="font-size: 16px; color: #333;">Your One-Time Password (OTP) is:</p>
              <div style="text-align: center; margin: 20px 0;">
                <span style="font-size: 28px; letter-spacing: 5px; font-weight: bold; color: #4CAF50;">%s</span>
              </div>
              <p style="font-size: 14px; color: #555;">This code is valid for <b>5 minutes</b>. Please do not share it with anyone.</p>
              <hr style="margin: 20px 0;"/>
              <p style="font-size: 12px; color: #999; text-align: center;">© 2025 Loopus App - All rights reserved</p>
            </div>
          </body>
        </html>
        """.formatted(otp);
    }
}
