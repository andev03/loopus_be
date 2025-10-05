package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.DebtStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class DebtDto {
    private UUID debtId;

    private UsersDto fromUser;

    private UsersDto toUser;

    private BigDecimal amount;

    private DebtStatus status;

    private OffsetDateTime dueDate;
}
