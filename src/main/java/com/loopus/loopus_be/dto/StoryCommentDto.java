package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class StoryCommentDto {
    private UUID commentId;

    private UsersDto user;

    private String content;

    private OffsetDateTime createdAt;
}
