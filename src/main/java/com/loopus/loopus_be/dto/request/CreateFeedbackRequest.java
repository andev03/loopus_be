package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateFeedbackRequest {

    @NotNull(message = "Vui lòng nhập userId")
    private UUID userId;

    @NotNull(message = "Vui lòng nhập type")
    private String type;

    @NotNull(message = "Vui lòng nhập content")
    private String content;
}
