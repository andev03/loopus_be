package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.LoginRequest;
import com.loopus.loopus_be.dto.request.RegisterRequest;
import com.loopus.loopus_be.dto.request.UpdateUserRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IUserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final IUserService userService;

    @PostMapping("/login")
    public ResponseDto<Object> login(@Valid @RequestBody LoginRequest request) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Đăng nhập thành công!")
                .data(userService.login(request.getUsername(), request.getPassword()))
                .build();
    }

    @PostMapping("/register")
    public ResponseDto<Object> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Đăng ký thành công!")
                .data(userService.register(request))
                .build();
    }

    @PostMapping("/otp-forgot-password")
    public ResponseDto<Object> otpForgotPassword(@RequestParam String email) {
        userService.otpForgotPassword(email);
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Gửi yêu cầu thành công")
                .build();
    }

    @GetMapping("/find-by-email")
    public ResponseDto<Object> findUserByEmail(@RequestParam String email) {
        return ResponseDto.builder()
                .data(userService.findUserByEmail(email))
                .status(HttpStatus.OK.value())
                .message("Gửi yêu cầu thành công")
                .build();
    }

    @PutMapping(value = "/update-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<Object> updateAvatar(
            @Parameter(description = "File to update", required = true)
            @RequestPart("file") MultipartFile file, @RequestParam UUID userId) {

        return ResponseDto.builder()
                .data(userService.updateAvatar(userId, file))
                .status(HttpStatus.OK.value())
                .message("Cập nhật avatar thành công")
                .build();
    }

    @PutMapping(value = "/update-information")
    public ResponseDto<Object> updateInformation(@RequestBody @Valid UpdateUserRequest request) {

        return ResponseDto.builder()
                .data(userService.updateInformation(request))
                .status(HttpStatus.OK.value())
                .message("Cập nhật thông tin thành công")
                .build();
    }
}
