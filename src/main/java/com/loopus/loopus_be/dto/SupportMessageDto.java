package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class SupportMessageDto {
    private UUID chatId;

    private UUID messageId;

    private UsersDto sender;

    private String message;

    private Instant createdAt;
}
