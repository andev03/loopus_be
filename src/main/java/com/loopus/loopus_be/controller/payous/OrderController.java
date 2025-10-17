package com.loopus.loopus_be.controller.payous;

import com.loopus.loopus_be.dto.payos.ApiResponse;
import com.loopus.loopus_be.dto.payos.CreatePaymentLinkRequestBody;
import com.loopus.loopus_be.enums.TransactionStatus;
import com.loopus.loopus_be.enums.TransactionType;
import com.loopus.loopus_be.model.Transaction;
import com.loopus.loopus_be.repository.TransactionRepository;
import com.loopus.loopus_be.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLink;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;
import vn.payos.model.webhooks.ConfirmWebhookResponse;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final PayOS payOS;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Value("${base-url}")
    private String baseUrl;

    @PostMapping(path = "/create")
    public ApiResponse<CreatePaymentLinkResponse> createPaymentLink(
            @RequestBody CreatePaymentLinkRequestBody RequestBody) {

        final String productName = "Gói thành viên Loopus";
        final String description = "Mua gói thành viên Loopus";
        final String returnUrl = baseUrl + "/success";
        final String cancelUrl = baseUrl + "/cancel";
        final long price = RequestBody.getPrice();
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

        createTransactionRecord(orderCode, RequestBody.getUserId(), price);

        return ApiResponse.success(data);

    }

    public ApiResponse<PaymentLink> getOrderById(long orderId) {
        PaymentLink order = payOS.paymentRequests().get(orderId);
        return ApiResponse.success("ok", order);
    }

    public ApiResponse<PaymentLink> cancelOrder(long orderId, String reason) {
        PaymentLink order = payOS.paymentRequests().cancel(orderId, reason);
        return ApiResponse.success("ok", order);

    }

    @PostMapping(path = "/confirm-webhook")
    @Hidden
    public ApiResponse<ConfirmWebhookResponse> confirmWebhook(
            @RequestBody Map<String, String> requestBody) {
        ConfirmWebhookResponse result = payOS.webhooks().confirm(requestBody.get("webhookUrl"));
        return ApiResponse.success("ok", result);
    }

    private void createTransactionRecord(
            Long orderCode, UUID userId, Long amount
    ) {
        transactionRepository.save(
                Transaction.builder()
                        .orderCode(orderCode)
                        .user(userRepository.getReferenceById(userId))
                        .amount(BigDecimal.valueOf(amount))
                        .transactionType(TransactionType.MEMBERSHIP)
                        .status(TransactionStatus.PENDING)
                        .description("Mua gói thành viên Loopus")
                        .build()
        );
    }
}
