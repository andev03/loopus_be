package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.model.Wallet;
import com.loopus.loopus_be.model.WalletTransaction;
import com.loopus.loopus_be.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
@Valid
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{userId}")
    public ResponseDto<Object> getWallet(@PathVariable UUID userId) {
        return ResponseDto.builder()
                .data(walletService.getWalletByUserId(userId))
                .status(HttpStatus.OK.value())
                .message("Xem ví thành công")
                .build();
    }

//    @PostMapping("/{userId}/deposit")
//    public ResponseDto<Object> deposit(
//            @PathVariable UUID userId,
//            @RequestParam Double amount) {
//        return ResponseDto.builder()
//                .data(walletService.deposit(userId, amount))
//                .status(HttpStatus.OK.value())
//                .message("Nộp tiền thành công")
//                .build();
//    }

    @PostMapping("/transfer")
    public ResponseDto<Object> transfer(
            @RequestParam UUID senderId, @RequestParam UUID receiverId,
            @RequestParam Double amount, @RequestParam UUID groupId
    ) {
        walletService.transfer(senderId, receiverId, amount, groupId);
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Chuyển tiền thành công")
                .build();
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseDto<Object> getTransactions(@PathVariable UUID walletId) {
        return ResponseDto.builder()
                .data(walletService.getTransactions(walletId))
                .status(HttpStatus.OK.value())
                .message("Xem ví giao dịch")
                .build();
    }

    @GetMapping("/transaction/{walletTransactionId}")
    public ResponseDto<Object> getTransactionDetail(@PathVariable UUID walletTransactionId) {
        return ResponseDto.builder()
                .data(walletService.getTransactionDetail(walletTransactionId))
                .status(HttpStatus.OK.value())
                .message("Xem chi tiết giao dịch thành công!")
                .build();
    }
}