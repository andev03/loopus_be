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
}
