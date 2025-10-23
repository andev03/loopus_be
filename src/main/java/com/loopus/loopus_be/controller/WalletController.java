package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.payos.ApiResponse;
import com.loopus.loopus_be.dto.payos.CreatePaymentLinkRequestBody;
import com.loopus.loopus_be.dto.request.TransferRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.enums.TransactionStatus;
import com.loopus.loopus_be.enums.TransactionType;
import com.loopus.loopus_be.model.Transaction;
import com.loopus.loopus_be.repository.TransactionRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IWalletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@Valid
public class WalletController {

    private final IWalletService iWalletService;
    private final TransactionRepository transactionRepository;
    private final PayOS payOS;
    private final UserRepository userRepository;

    @Value("${base-url}")
    private String baseUrl;

    public WalletController(IWalletService iWalletService, @Qualifier("payOSCheckout") PayOS payOS, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.iWalletService = iWalletService;
        this.payOS = payOS;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public ResponseDto<Object> getWallet(@PathVariable UUID userId) {
        return ResponseDto.builder()
                .data(iWalletService.getWalletByUserId(userId))
                .status(HttpStatus.OK.value())
                .message("Xem ví thành công")
                .build();
    }

    @PostMapping(path = "/deposit")
    public ApiResponse<CreatePaymentLinkResponse> createPaymentLink(
            @RequestBody CreatePaymentLinkRequestBody requestBody) {

        final String productName = "Nạp tiền vào ví";
        final String description = "Nạp tiền vào ví";
        final String returnUrl = baseUrl + "/success_deposit";
        final String cancelUrl = baseUrl + "/cancel_deposit";
        final long price = requestBody.getPrice();
        long orderCode = System.currentTimeMillis() / 1000;

        PaymentLinkItem item =
                PaymentLinkItem.builder().name(productName).quantity(1).price(price).build();

        CreatePaymentLinkRequest paymentData =
                CreatePaymentLinkRequest.builder()
                        .orderCode(orderCode)
                        .description(description)
                        .amount(price)
                        .item(item)
                        .returnUrl(returnUrl)
                        .cancelUrl(cancelUrl)
                        .build();

        CreatePaymentLinkResponse data = payOS.paymentRequests().create(paymentData);

        createTransactionRecordForDeposit(orderCode, requestBody.getUserId(), price);

        iWalletService.deposit(requestBody.getUserId(), (double) requestBody.getPrice());

        return ApiResponse.success(data);
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
                .message("Xem ví giao dịch!")
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

    private void createTransactionRecordForDeposit(
            Long orderCode, UUID userId, Long amount
    ) {
        transactionRepository.save(
                Transaction.builder()
                        .orderCode(orderCode)
                        .user(userRepository.getReferenceById(userId))
                        .amount(BigDecimal.valueOf(amount))
                        .transactionType(TransactionType.DEPOSIT)
                        .status(TransactionStatus.PENDING)
                        .description("Nạp tiền vào ví")
                        .build()
        );
    }
}