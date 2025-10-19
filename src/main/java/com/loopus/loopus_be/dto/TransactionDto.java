package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.TransactionStatus;
import com.loopus.loopus_be.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionDto {
    private UUID transactionId;

    private Long orderCode;

    private UsersDto user;

    private BigDecimal amount;

    private TransactionType transactionType;

    private TransactionStatus status;

    private String description;

    private OffsetDateTime createdAt;
}
