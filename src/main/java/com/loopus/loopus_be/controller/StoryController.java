package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Validated
public class StoryController {

    @PostMapping
    @Operation(summary = "Tạo story (followers hoặc group)")
    public ResponseDto<Object> createStory() {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @GetMapping("/feed")
    @Operation(summary = "Lấy danh sách story mà mình được xem")
    public ResponseDto<Object> getStoryFeed() {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Xem story của 1 user cụ thể")
    public ResponseDto<Object> getUserStories(@PathVariable UUID userId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @DeleteMapping("/{storyId}")
    @Operation(summary = "Xóa story")
    public ResponseDto<Object> deleteStory(@PathVariable UUID storyId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }
}
