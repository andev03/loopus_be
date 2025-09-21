package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.LoginRequest;
import com.loopus.loopus_be.dto.request.RegisterRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

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
}
