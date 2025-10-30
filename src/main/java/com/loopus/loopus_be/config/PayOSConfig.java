package com.loopus.loopus_be.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {

    // ===== Checkout Config =====
    @Value("${payos.checkout.client-id}")
    private String checkoutClientId;

    @Value("${payos.checkout.api-key}")
    private String checkoutApiKey;

    @Value("${payos.checkout.checksum-key}")
    private String checkoutChecksumKey;

    // ===== Payout Config =====
    @Value("${payos.payout.client-id}")
    private String payoutClientId;

    @Value("${payos.payout.api-key}")
    private String payoutApiKey;

    @Value("${payos.payout.checksum-key}")
    private String payoutChecksumKey;

    @Bean("payOSCheckout")
    public PayOS payOSCheckout() {
        return new PayOS(checkoutClientId, checkoutApiKey, checkoutChecksumKey);
    }

    @Bean("payOSPayout")
    public PayOS payOSPayout() {
        return new PayOS(payoutClientId, payoutApiKey, payoutChecksumKey);
    }
}
