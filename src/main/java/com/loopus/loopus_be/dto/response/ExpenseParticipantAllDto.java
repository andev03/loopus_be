package com.loopus.loopus_be.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseParticipantAllDto {
    private UUID debtorId;
    private String debtorName;
    private UUID paidToUserId;
    private BigDecimal totalOwedAmount;
    private boolean allPaid;
}
