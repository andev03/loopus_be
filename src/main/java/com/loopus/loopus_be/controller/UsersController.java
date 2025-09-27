package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.LoginRequest;
import com.loopus.loopus_be.dto.request.RegisterRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final IUserService userService;
    @Value("${file.upload-dir}")
    private String uploadDir;

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

    @Operation(summary = "Upload file")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "File uploaded successfully") })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadFile(
            @Parameter(description = "File to upload")
            @RequestPart("file") MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);

        // Tạo folder nếu chưa có
        Files.createDirectories(path.getParent());

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        String fileUrl = "https://nguyenhoangan03.site/uploads/" + filename;
        Map<String, String> response = new HashMap<>();
        response.put("url", fileUrl);

        return ResponseEntity.ok(response);
    }

}
