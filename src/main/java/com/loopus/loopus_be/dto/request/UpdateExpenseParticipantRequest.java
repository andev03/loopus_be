package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class UpdateExpenseParticipantRequest {
    @NotNull(message = "Vui lòng nhập expenseParticipantId")
    private UUID expenseParticipantId;

    @NotNull(message = "Vui lòng nhập shareAmount")
    private BigDecimal shareAmount;
}
