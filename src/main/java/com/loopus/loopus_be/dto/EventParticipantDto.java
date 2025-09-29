package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.ParticipationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventParticipantDto {

    private UsersDto user;

    private ParticipationStatus status;

    private LocalDateTime respondedAt;
}
