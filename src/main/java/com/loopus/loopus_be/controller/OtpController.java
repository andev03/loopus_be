package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.ForgotPasswordRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.enums.UserStatusEnum;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IOtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
@Validated
public class OtpController {
    private final IOtpService otpService;
    private final UserRepository userRepository;

    @PostMapping("/verify-register")
    public ResponseDto<Object> verifyOtpRegister(@RequestParam String email, @RequestParam String otp) {
        boolean flag = otpService.verifyOtp(email, otp);

        Users user = userRepository.findByUsername(email);

        if (flag) {
            user.setStatus(UserStatusEnum.ACTIVE);
            userRepository.save(user);
            return ResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("OTP có hiệu lực. Đăng ký thành công.").build();
        }
        userRepository.delete(user);

        return ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("OTP không có hiệu lực. Đăng ký thất bại.").build();
    }

    @PostMapping("/verify-forgot-password")
    public ResponseDto<Object> verifyOtpForgotPassword(@RequestParam String email, @RequestParam String otp) {
        boolean flag = otpService.verifyOtp(email, otp);

        if (flag) {
            return ResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("OTP có hiệu lực.").build();
        }

        return ResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("OTP không có hiệu lực.").build();
    }

    @PutMapping("/reset-password")
    public ResponseDto<Object> resetPassword(@RequestBody @Valid ForgotPasswordRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Hai password không giống nhau!").build();
        }

        Users user = userRepository.findByUsername(request.getEmail());
        user.setPasswordHash(request.getPassword());
        userRepository.save(user);
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Thay đổi mật khẩu thành công.").build();
    }
}
