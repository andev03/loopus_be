package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.ISupportChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
@Validated
public class SupportChatController {

    private final ISupportChatService iSupportChatService;

    @GetMapping("/chat")
    @Operation(summary = "status: NOT_YET(Dùng để filter những chat chưa được hỗ trợ, " +
            "RECEPTION: là những chat đã được hỗ trợ.")
    public ResponseDto<Object> getNotYet(@RequestParam String status) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iSupportChatService.getNotYetSupportChat(status))
                .message("Xem tin nhắn thành công!")
                .build();
    }

    @GetMapping("/{chatId}/messages")
    @Operation(summary = "Lấy tất cả tin nhắn trong 1 box chat!")
    public ResponseDto<Object> getAllMessageByChatId(@PathVariable UUID chatId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iSupportChatService.getAllMessageByChatId(chatId))
                .message("Xem tin nhắn thành công!")
                .build();
    }


    @PutMapping("/{chatId}/reception")
    @Operation(summary = "Admin tiếp nhận chat")
    public ResponseDto<Object> reception(@PathVariable UUID chatId, @RequestParam UUID adminId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iSupportChatService.reception(chatId, adminId))
                .message("Tiếp nhận thành công!")
                .build();
    }

    @PostMapping("/{userId}/chat")
    @Operation(summary = "User chat")
    public ResponseDto<Object> userChat(@PathVariable UUID userId, @RequestParam String message) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iSupportChatService.userChat(userId, message))
                .message("Nhắn tin thành công!")
                .build();
    }

    @PostMapping("/{adminId}/{chatId}")
    @Operation(summary = "User chat")
    public ResponseDto<Object> adminChat(@PathVariable UUID adminId, @PathVariable UUID chatId, @RequestParam String message) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iSupportChatService.adminChat(adminId, chatId, message))
                .message("Nhắn tin thành công!")
                .build();
    }
}
