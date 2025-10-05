package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ExpenseDto {
    private UUID expenseId;

    private String description;

    private BigDecimal amount;

    private UsersDto paidBy;

    private OffsetDateTime createdAt;

    private List<ExpenseParticipantDto> participants;
}
