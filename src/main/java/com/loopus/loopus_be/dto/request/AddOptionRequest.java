package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddOptionRequest {
    @NotNull(message = "Vui lòng nhập pollId!")
    private UUID pollId;

    @NotNull(message = "Vui lòng nhập optionText!")
    private String optionText;
}
