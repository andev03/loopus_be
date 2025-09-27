package com.loopus.loopus_be.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatTextRequest {
    private UUID groupId;
    private UUID userId;
    private String message;
}
