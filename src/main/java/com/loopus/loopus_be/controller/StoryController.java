package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.CreateStoryRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IStoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Validated
public class StoryController {

    private final IStoryService iStoryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Tạo story (visibilityType là followers hoặc group)")
    public ResponseDto<Object> createStory(
            @Parameter(description = "Request JSON", content = @Content(mediaType = "application/json"))
            @RequestPart("request") @Valid CreateStoryRequest request,
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryService.createStory(request, file))
                .message("Tạo story thành công!")
                .build();
    }

    @GetMapping("/{userId}/feed")
    @Operation(summary = "Lấy danh sách tất cả story mà mình được xem")
    public ResponseDto<Object> getStoryFeed(@PathVariable UUID userId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryService.getStoryFeed(userId))
                .message("Lấy tất cả story thành công!")
                .build();
    }

    @GetMapping("/{storyId}/detail")
    @Operation(summary = "Lấy chi tiết story")
    public ResponseDto<Object> getStoryDetail(@PathVariable UUID storyId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryService.getStoryDetail(storyId))
                .message("Lấy tất cả story thành công!")
                .build();
    }

    @GetMapping("/{albumId}")
    @Operation(summary = "Lấy danh sách tất cả story của 1 album")
    public ResponseDto<Object> getStoryByAlbumId(@PathVariable UUID albumId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryService.getStoryByAlbumId(albumId))
                .message("Lấy tất cả story của 1 album thành công!")
                .build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Xem story của 1 user cụ thể")
    public ResponseDto<Object> getUserStories(@PathVariable UUID userId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryService.getUserStories(userId))
                .message("Lấy tất cả story của 1 user thành công!")
                .build();
    }

    @DeleteMapping("/{storyId}")
    @Operation(summary = "Xóa story")
    public ResponseDto<Object> deleteStory(@PathVariable UUID storyId) {

        iStoryService.deleteStory(storyId);

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Xoá story thành công!")
                .build();
    }
}
