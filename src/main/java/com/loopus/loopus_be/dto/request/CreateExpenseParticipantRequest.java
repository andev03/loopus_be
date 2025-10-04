package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateExpenseParticipantRequest {
    @NotNull(message = "Vui lòng nhập userId")
    private UUID userId;

    @NotNull(message = "Vui lòng nhập shareAmount")
    private BigDecimal shareAmount;
}
