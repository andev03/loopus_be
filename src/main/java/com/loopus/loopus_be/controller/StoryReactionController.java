package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Validated
public class StoryReactionController {

    @PostMapping("/{storyId}/reactions")
    @Operation(summary = "Thả cảm xúc (emoji)")
    public ResponseDto<Object> addReaction(@PathVariable UUID storyId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @DeleteMapping("/{storyId}/reactions")
    @Operation(summary = "Gỡ reaction")
    public ResponseDto<Object> removeReaction(@PathVariable UUID storyId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @GetMapping("/{storyId}/reactions")
    @Operation(summary = "Xem tất cả reaction theo story")
    public ResponseDto<Object> getReactions(@PathVariable UUID storyId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }
}
