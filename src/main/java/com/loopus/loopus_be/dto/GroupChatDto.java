package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.MessageTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class GroupChatDto {
    private UUID chatId;

    private UUID senderId;

    private String message;

    private MessageTypeEnum type;

    private OffsetDateTime createdAt;

    private String imageUrl;
}
