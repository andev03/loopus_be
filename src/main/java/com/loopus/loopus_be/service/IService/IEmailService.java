package com.loopus.loopus_be.service.IService;

import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendOtpEmail(String to, String otp);
}
