package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PollDto {

    private UUID pollId;

    private GroupDto group;

    private UsersDto createdBy;

    private OffsetDateTime createdAt;

    private List<PollOptionDto> options;
}
