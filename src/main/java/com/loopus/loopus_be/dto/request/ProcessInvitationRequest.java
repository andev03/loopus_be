package com.loopus.loopus_be.dto.request;

import com.loopus.loopus_be.enums.ParticipationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProcessInvitationRequest {
    @NotNull(message = "Vui lòng nhập eventId!")
    private UUID eventId;

    @NotNull(message = "Vui lòng nhập userId!")
    private UUID userId;

    @NotNull(message = "Vui lòng nhập status!")
    private ParticipationStatus status;
}
