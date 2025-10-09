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
public class StoryCommentController {

    @PostMapping("/{storyId}/comments")
    @Operation(summary = "Thêm bình luận")
    public ResponseDto<Object> addComment(@PathVariable UUID storyId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @GetMapping("/{storyId}/comments")
    @Operation(summary = "Lấy danh sách comment")
    public ResponseDto<Object> getCommentsByStoryId(@PathVariable UUID storyId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }

    @DeleteMapping("/{storyId}/comments/{commentId}")
    @Operation(summary = "Xóa comment (chủ comment hoặc chủ story)")
    public ResponseDto<Object> deleteComment(@PathVariable UUID storyId, @PathVariable UUID commentId) {
        return ResponseDto.builder()
                .data(null)
                .message(null)
                .build();
    }
}
