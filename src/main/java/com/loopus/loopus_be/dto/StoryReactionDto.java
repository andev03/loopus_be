package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class StoryReactionDto {
    private UUID reactionId;

    private UsersDto user;

    private String emoji;

    private OffsetDateTime createdAt;
}
