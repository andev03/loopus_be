package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.VisibilityType;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class StoryDto {
    private UUID storyId;

    private UsersDto user;

    private String imageUrl;

    private String caption;

    private VisibilityType visibilityType;

    private OffsetDateTime createdAt;

    private OffsetDateTime expiresAt;
}
