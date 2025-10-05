package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateSettingRequest {

    @NotNull(message = "Vui lòng nhập settingId!")
    private UUID settingId;

    @NotNull(message = "Vui lòng nhập enabled!")
    private boolean enabled;
}
