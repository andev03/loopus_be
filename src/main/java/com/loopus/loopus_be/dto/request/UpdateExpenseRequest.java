package com.loopus.loopus_be.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateExpenseRequest {

    @NotNull(message = "Vui lòng nhập groupId")
    private UUID expenseId;

    @NotNull(message = "Vui lòng nhập description")
    private String description;

    @Min(value = 1, message = "Khoản chi không được thấp hơn 1")
    private BigDecimal amount;

    @NotNull(message = "Vui lòng nhập paidById")
    private UUID paidById;

    @NotNull(message = "Vui lòng nhập expenseParticipant")
    private List<@Valid UpdateExpenseParticipantRequest> expenseParticipant;

    @NotNull(message = "Vui lòng nhập type")
    private String type; // Chia đều, chia theo số tiền đã nhập
}
