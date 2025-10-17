package com.loopus.loopus_be.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {

    @Value("${client-id}")
    private String clientId;

    @Value("${api-key}")
    private String apiKey;

    @Value("${checksum-key}")
    private String checksumKey;

    @Bean
    public PayOS payOS() {
        // Khởi tạo PayOS với các thông tin cần thiết
        String clientId = this.clientId;
        String apiKey = this.apiKey;
        String checksumKey = this.checksumKey;
        return new PayOS(clientId, apiKey, checksumKey);
    }
}
