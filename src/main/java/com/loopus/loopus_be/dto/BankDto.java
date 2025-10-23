package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class BankDto {
    private UUID bankId;

    private String bankName;

    private String binCode;

    private LocalDateTime createdAt;
}
