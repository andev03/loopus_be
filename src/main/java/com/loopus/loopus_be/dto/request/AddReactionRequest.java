package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddReactionRequest {
    @NotNull(message = "Vui lòng nhập storyId!")
    private UUID storyId;

    @NotNull(message = "Vui lòng nhập userId!")
    UUID userId;

    @NotNull(message = "Vui lòng nhập emoji!")
    String emoji;
}
