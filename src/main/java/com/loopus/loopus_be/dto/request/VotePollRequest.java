package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VotePollRequest {

    @NotNull(message = "Vui lòng nhập pollId!")
    private UUID pollId;

    @NotNull(message = "Vui lòng nhập userId!")
    private UUID userId;

    @NotNull(message = "Vui lòng nhập optionId!")
    private UUID optionId;
}
