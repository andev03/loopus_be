package com.loopus.loopus_be.service.IService;

public interface IOtpService {
    String generateOtp(String email);
    boolean verifyOtp(String email, String otp);
}
