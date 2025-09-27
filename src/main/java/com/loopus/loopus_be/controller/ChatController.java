package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.ChatTextRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/chats/chat-text")
    public ResponseDto<Object> chatTextType(@RequestBody ChatTextRequest request) {
        return ResponseDto.builder()
                .data(iChatService.sendMessage(request))
                .message("Gửi tin nhắn thành công")
                .build();
    }
}
