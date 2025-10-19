package com.loopus.loopus_be.controller.payous;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.loopus.loopus_be.dto.payos.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.payos.PayOS;
import vn.payos.model.webhooks.WebhookData;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PayOS payOS;

    public PaymentController(PayOS payOS) {
        super();
        this.payOS = payOS;
    }

    @PostMapping(path = "/payos_transfer_handler")
    @Hidden
    public ApiResponse<WebhookData> payosTransferHandler(@RequestBody Object body)
            throws JsonProcessingException, IllegalArgumentException {
        WebhookData data = payOS.webhooks().verify(body);
        System.out.println(data);
        return ApiResponse.success("Webhook delivered", data);
    }
}
