package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class ExpenseParticipantDto {
    private UUID id;

    private UsersDto user;

    private ExpenseDto expenseDto;

    private BigDecimal shareAmount;

    private boolean isPaid = false;

    private OffsetDateTime paidAt;
}
