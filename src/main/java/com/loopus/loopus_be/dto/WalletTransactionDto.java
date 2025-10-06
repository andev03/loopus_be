package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class WalletTransactionDto {
    private UUID transactionId;

    private Double amount;

    private TransactionType type;

    private UsersDto relatedUser;

    private String description;

    private Instant createdAt;
}
