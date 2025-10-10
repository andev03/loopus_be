package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateStoryRequest {

    @NotNull(message = "Vui lòng nhập userId")
    private UUID userId;

    @NotNull(message = "Vui lòng nhập caption")
    private String caption;

    @NotNull(message = "Vui lòng nhập visibilityType")
    private String visibilityType;

    private UUID albumId;
}
