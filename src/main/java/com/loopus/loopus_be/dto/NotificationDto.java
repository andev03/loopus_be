package com.loopus.loopus_be.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDto {
    private UUID notificationId;

    private UsersDto user;

    private UsersDto sender;

    private GroupDto group;

    private String type;

    private String title;

    private String message;

    private BigDecimal amount;

    private Boolean isRead;

    private Instant createdAt;
}
