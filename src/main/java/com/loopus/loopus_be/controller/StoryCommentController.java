package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.CreateCommentRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IStoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Validated
public class StoryCommentController {

    private final IStoryService iStoryService;

    @PostMapping("/{storyId}/comments")
    @Operation(summary = "Thêm bình luận")
    public ResponseDto<Object> addComment(@RequestBody @Valid CreateCommentRequest request) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryService.addComment(request.getStoryId(), request.getUserId(), request.getContent()))
                .message("Comment thành công!")
                .build();
    }

    @GetMapping("/{storyId}/comments")
    @Operation(summary = "Lấy danh sách comment")
    public ResponseDto<Object> getCommentsByStoryId(@PathVariable UUID storyId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryService.getCommentsByStoryId(storyId))
                .message("Lấy danh sách comment thành công!")
                .build();
    }

    @DeleteMapping("/{storyId}/comments/{commentId}")
    @Operation(summary = "Xóa comment hoặc story (nếu commentId null thì xóa story)")
    public ResponseDto<Object> deleteComment(
            @PathVariable UUID storyId,
            @RequestParam(required = false) UUID commentId
    ) {

        iStoryService.deleteCommentOrStory(storyId, commentId);

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Xoá comment hoặc story thành công!")
                .build();
    }

    @PutMapping("/comment/{commentId}")
    @Operation(summary = "Cập nhật comment")
    public ResponseDto<Object> updateComment(
            @PathVariable UUID commentId,
            @RequestBody @Valid @NotNull(message = "Vui lòng nhập content!") String content
    ) {

        if (content != null && content.startsWith("\"") && content.endsWith("\"")) {
            content = content.substring(1, content.length() - 1);
        }

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryService.updateComment(commentId, content))
                .message("Xoá comment thành công!")
                .build();
    }
}
