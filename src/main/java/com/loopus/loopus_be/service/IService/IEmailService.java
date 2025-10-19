package com.loopus.loopus_be.service.IService;

public interface IEmailService {
    void sendOtpEmail(String to, String otp);

    void sendOtpEmailForSuccess(
            String to, String otp, String transactionId,
            String amount,
            String time,
            String orderCode,
            String paymentType,
            String description,
            String status
    );

    void sendOtpEmailForFailed(
            String to, String otp, String transactionId,
            String amount,
            String time,
            String orderCode,
            String paymentType,
            String description,
            String status
    );
}
