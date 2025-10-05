package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.FeedbackType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class FeedbackDto {
    private UUID feedbackId;

    private UsersDto user;

    private FeedbackType type;

    private String content;

    private String imageUrl;

    private Instant createdAt = Instant.now();
}
