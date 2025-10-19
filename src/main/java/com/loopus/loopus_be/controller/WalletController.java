package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.TransferRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IWalletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
@Valid
public class WalletController {

    private final IWalletService iWalletService;

    @GetMapping("/{userId}")
    public ResponseDto<Object> getWallet(@PathVariable UUID userId) {
        return ResponseDto.builder()
                .data(iWalletService.getWalletByUserId(userId))
                .status(HttpStatus.OK.value())
                .message("Xem ví thành công")
                .build();
    }

    @PostMapping("/{userId}/deposit")
    public ResponseDto<Object> deposit(
            @PathVariable UUID userId,
            @RequestParam Double amount) {
        return ResponseDto.builder()
                .data(iWalletService.deposit(userId, amount))
                .status(HttpStatus.OK.value())
                .message("Nộp tiền thành công")
                .build();
    }

    @PostMapping("/transfer")
    @Operation(summary = "Chuyển tiền từ ví người này sang ví người khác",
            description = "type: INDIVIDUAL_TRANSFER, GROUP_EXPENSE")
    public ResponseDto<Object> transfer(@RequestBody @Valid TransferRequest request) {
        iWalletService.transfer(request);
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Chuyển tiền thành công")
                .build();
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseDto<Object> getTransactions(@PathVariable UUID walletId) {
        return ResponseDto.builder()
                .data(iWalletService.getTransactions(walletId))
                .status(HttpStatus.OK.value())
                .message("Xem ví giao dịch")
                .build();
    }

    @GetMapping("/transaction/{walletTransactionId}")
    public ResponseDto<Object> getTransactionDetail(@PathVariable UUID walletTransactionId) {
        return ResponseDto.builder()
                .data(iWalletService.getTransactionDetail(walletTransactionId))
                .status(HttpStatus.OK.value())
                .message("Xem chi tiết giao dịch thành công!")
                .build();
    }
}