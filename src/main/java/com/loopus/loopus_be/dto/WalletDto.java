package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class WalletDto {
    private UUID walletId;

    private UsersDto user;

    private Double balance;

    private Instant updatedAt;

    private List<WalletTransactionDto> transactions;
}
