package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TransferRequest {

    @NotNull(message = "SenderId cannot be null")
    private UUID senderId;

    @NotNull(message = "receiverId cannot be null")
    private UUID receiverId;

    @NotNull(message = "amount cannot be null")
    private BigDecimal amount;

    private UUID expenseId;

    private UUID groupId;

    @NotNull(message = "typeTransfer cannot be null")
    private String typeTransfer;
}
