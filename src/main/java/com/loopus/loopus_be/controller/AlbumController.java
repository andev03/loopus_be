package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
@Validated
public class AlbumController {

    @PostMapping
    @Operation(summary = "Tạo album nhóm")
    public ResponseDto<Object> createAlbum() {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @PostMapping("/{albumId}/members/{userId}")
    @Operation(summary = "Thêm thành viên")
    public ResponseDto<Object> addMemberToAlbum(@PathVariable UUID albumId, @PathVariable UUID userId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @DeleteMapping("/{albumId}/members/{userId}")
    @Operation(summary = "Xóa thành viên")
    public ResponseDto<Object> removeMemberFromAlbum(@PathVariable UUID albumId, @PathVariable UUID userId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @GetMapping("/{albumId}")
    @Operation(summary = "Xem chi tiết album")
    public ResponseDto<Object> getAlbumDetail(@PathVariable UUID albumId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @GetMapping("/{albumId}/members")
    @Operation(summary = "Danh sách thành viên")
    public ResponseDto<Object> getAlbumMembers(@PathVariable UUID albumId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @GetMapping("/{albumId}/stories")
    @Operation(summary = "Lấy danh sách story trong album")
    public ResponseDto<Object> getStoriesByAlbumId(@PathVariable UUID albumId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }
}
