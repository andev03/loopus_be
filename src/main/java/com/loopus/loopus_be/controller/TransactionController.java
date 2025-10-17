package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final ITransactionService iTransactionService;

    @GetMapping("/{userId}/user")
    @Operation(summary = "Get all transactions of a user",
            description = "transactionType: DEPOSIT, MEMBERSHIP(không yêu cầu nếu có thì là filter theo loại giao dịch)")
    public ResponseDto<Object> getAllOfUser(
            @PathVariable UUID userId, @RequestParam(required = false) String transactionType) {

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iTransactionService.getAllOfUserAndType(userId, transactionType))
                .message("Gửi yêu cầu thành công")
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all transactions",
            description = "transactionType: DEPOSIT, MEMBERSHIP (không yêu cầu nếu có thì là filter theo loại giao dịch)")
    public ResponseDto<Object> getAll(@RequestParam(required = false) String transactionType) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iTransactionService.getAllByType(transactionType))
                .message("Gửi yêu cầu thành công")
                .build();
    }

    @GetMapping("/{transactionId}")
    public ResponseDto<Object> getTransactionDetail(@PathVariable UUID transactionId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iTransactionService.getTransactionDetail(transactionId))
                .message("Gửi yêu cầu thành công")
                .build();
    }
}
