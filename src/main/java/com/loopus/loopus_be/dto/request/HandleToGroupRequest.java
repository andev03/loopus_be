package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HandleToGroupRequest {

    @NotNull(message = "Vui lòng nhập senderId")
    private UUID senderId;

    @NotNull(message = "Vui lòng nhập groupId")
    private UUID groupId;

    @NotNull(message = "Vui lòng nhập userId")
    private UUID userId;
}
