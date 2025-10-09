package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IFollowService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
@Validated
public class FollowController {

    private final IFollowService iFollowService;

    @PostMapping("/{userId}/{targetUserId}")
    @Operation(summary = "Follow user")
    public ResponseDto<Object> followUser(@PathVariable UUID userId, @PathVariable UUID targetUserId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iFollowService.followUser(userId, targetUserId))
                .message("Đã follow thành công!")
                .build();
    }

    @DeleteMapping("/{userId}/{targetUserId}")
    @Operation(summary = "Unfollow user")
    public ResponseDto<Object> unfollowUser(@PathVariable UUID userId, @PathVariable UUID targetUserId) {

        iFollowService.unfollowUser(userId, targetUserId);

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Đã unfollow thành công!")
                .build();
    }

    @GetMapping("/followers/{userId}")
    @Operation(summary = "Danh sách người đang follow userId")
    public ResponseDto<Object> getFollowersByUserId(@PathVariable UUID userId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iFollowService.getFollowersByUserId(userId))
                .message("Lấy danh sách followers thành công!")
                .build();
    }

    @GetMapping("/following/{userId}")
    @Operation(summary = "Danh sách người userId đang follow")
    public ResponseDto<Object> getFollowingByUserId(@PathVariable UUID userId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iFollowService.getFollowingByUserId(userId))
                .message("Lấy danh sách những người user đang theo dõi thành công!")
                .build();
    }

    @GetMapping("/status/{userId}/{targetUserId}")
    @Operation(summary = "Kiểm tra mình đã follow chưa")
    public ResponseDto<Object> checkFollowedYet(@PathVariable UUID userId, @PathVariable UUID targetUserId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iFollowService.checkFollowedYet(userId, targetUserId))
                .message("Kiểm tra thành công!")
                .build();
    }
}
