package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.ChatTextRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class ChatController {
    private final IChatService iChatService;

    @GetMapping("/chats")
    public ResponseDto<Object> getChatsByGroupId(@RequestParam UUID groupId) {
        return ResponseDto.builder()
                .data(iChatService.getMessagesByGroupId(groupId))
                .message("Lấy tin nhắn nhóm thành công")
                .build();
    }

    @GetMapping("/chats/image")
    public ResponseDto<Object> getAllImage(@RequestParam UUID groupId) {
        return ResponseDto.builder()
                .data(iChatService.getAllImageType(groupId))
                .message("Lấy toàn bộ hình ảnh nhóm thành công")
                .build();
    }

    @PostMapping("/chats/chat-text")
    public ResponseDto<Object> chatTextType(@RequestBody @Valid ChatTextRequest request) {
        return ResponseDto.builder()
                .data(iChatService.sendMessage(request))
                .message("Gửi tin nhắn thành công")
                .build();
    }

    @Operation(summary = "Send chat with image")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping(value = "/chats/chat-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<Object> chatImageType(
            @Parameter(description = "Request JSON", content = @Content(mediaType = "application/json"))
            @RequestPart("request") @Valid ChatTextRequest request,
            @RequestPart("file") MultipartFile file) {
        return ResponseDto.builder()
                .data(iChatService.sendImage(request, file))
                .message("Gửi tin nhắn thành công")
                .build();
    }

    @GetMapping("/chat")
    public ResponseDto<Object> findChatByChatText(@RequestParam UUID groupId, @RequestParam String message) {
        return ResponseDto.builder()
                .data(iChatService.findMessage(groupId, message))
                .message("Gửi tin nhắn thành công")
                .build();
    }
}
