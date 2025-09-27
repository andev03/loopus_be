package com.loopus.loopus_be.service.IService;

public interface IEmailService {
    void sendOtpEmail(String to, String otp);
}
