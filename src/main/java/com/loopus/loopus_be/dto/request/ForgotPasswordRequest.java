package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ForgotPasswordRequest {
    @NotBlank(message = "Vui lòng nhập email!")
    private String email;

    @NotBlank(message = "Vui lòng nhập password!")
    private String password;

    @NotBlank(message = "Vui lòng nhập confirm password!")
    private String confirmPassword;

    @NotBlank(message = "Vui lòng nhập OTP!")
    private String otp;
}
