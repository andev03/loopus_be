package com.loopus.loopus_be.dto.payos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentLinkRequestBody {

    @NotNull(message = "Vui lòng nhập userId")
    private UUID userId;

    @NotNull(message = "Vui lòng nhập price")
    @Min(value = 10000, message = "Số tiền phải nhiều hơn 10000")
    private int price;
}
