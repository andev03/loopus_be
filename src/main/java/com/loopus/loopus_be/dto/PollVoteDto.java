package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class PollVoteDto {
    private UUID voteId;

    private UsersDto user;

    private OffsetDateTime votedAt;
}
