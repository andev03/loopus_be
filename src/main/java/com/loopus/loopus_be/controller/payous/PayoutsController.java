package com.loopus.loopus_be.controller.payous;

import com.loopus.loopus_be.dto.payos.ApiResponse;
import com.loopus.loopus_be.service.IService.IWalletService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.model.v1.payouts.Payout;
import vn.payos.model.v1.payouts.PayoutRequests;
import vn.payos.model.v1.payoutsAccount.PayoutAccountInfo;

import java.util.UUID;

@RestController
@RequestMapping("/payouts")
public class PayoutsController {

    private final PayOS payOS;

    private final IWalletService iWalletService;

    public PayoutsController(@Qualifier("payOSPayout") PayOS payOS, IWalletService iWalletService) {
        this.payOS = payOS;
        this.iWalletService = iWalletService;
    }

    @PostMapping("/create")
    public ApiResponse<Payout> create(@RequestBody PayoutRequests body, @RequestParam UUID userId) {
        try {
            if (body.getReferenceId().isEmpty()) {
                body.setReferenceId("payout_" + (System.currentTimeMillis() / 1000));
            }

            Payout payout = payOS.payouts().create(body);

            iWalletService.payout(userId, Double.valueOf(body.getAmount()));

            return ApiResponse.success(payout);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("fail");
        }
    }

    @GetMapping("/balance")
    public ApiResponse<PayoutAccountInfo> getAccountBalance() {
        try {
            PayoutAccountInfo accountInfo = payOS.payoutsAccount().balance();
            return ApiResponse.success(accountInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("fail");
        }
    }
}
