package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.EventRepeat;
import com.loopus.loopus_be.enums.EventStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class GroupEventDto {
    private UUID eventId;

    private UsersDto creator;

    private UUID groupId;

    private String title;

    private String description;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private EventRepeat repeatType;

    private EventStatus status;

    private LocalDateTime createdAt;

    private Set<EventParticipantDto> participants;
}
