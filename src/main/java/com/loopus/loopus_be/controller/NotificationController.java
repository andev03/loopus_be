package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notifications/{userId}")
    public ResponseDto<Object> getNotifications(@PathVariable UUID userId) {
        return ResponseDto.builder()
                .data(notificationService.getNotifications(userId))
                .message("Lấy danh sách thông báo thành công!")
                .build();
    }

    @PutMapping("/{notificationId}/read")
    public ResponseDto<Object> markAsRead(@PathVariable UUID notificationId) {

        notificationService.markAsRead(notificationId);

        return ResponseDto.builder()
                .message("Đọc thông báo thành công!")
                .build();
    }

    @PutMapping("/notifications/{userId}/read-all")
    public ResponseDto<Object> markAsReadAll(@PathVariable UUID userId) {

        notificationService.markAllAsRead(userId);

        return ResponseDto.builder()
                .message("Đọc tất cả thông báo thành công!")
                .build();
    }
}
