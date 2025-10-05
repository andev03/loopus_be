package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.UpdateSettingRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.ISettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api")
public class SettingController {

    private final ISettingService iSettingService;

    @GetMapping("/settings")
    public ResponseDto<Object> getSettings() {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Đăng nhập thành công!")
                .data(iSettingService.getSettings())
                .build();
    }

    @GetMapping("/setting")
    public ResponseDto<Object> getSettingsByUserId(@RequestParam UUID userId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Đăng nhập thành công!")
                .data(iSettingService.getSettingsByUserId(userId))
                .build();
    }

    @PutMapping("/setting")
    public ResponseDto<Object> updateSettingsByUserId(@RequestBody @Valid List<UpdateSettingRequest> request) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Đăng nhập thành công!")
                .data(iSettingService.updateSettingsByUserId(request))
                .build();
    }
}
