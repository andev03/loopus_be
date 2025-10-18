package com.loopus.loopus_be.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupportChatDto {
    private UUID chatId;

    private UsersDto user;

    private UsersDto admin;

    private Instant createdAt;

    private String status;

    private List<SupportMessageDto> supportMessageDtos;
}
