package com.loopus.loopus_be.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNotificationRequest {

    private UUID receiverId;

    private UUID senderId;

    private UUID groupId;

    private String type;

    private String title;

    private String message;

    private BigDecimal amount;
}
