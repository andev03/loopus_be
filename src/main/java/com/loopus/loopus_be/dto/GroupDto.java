package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class GroupDto {

    private UUID groupId;

    private String name;

    private String description;

    private String avatarUrl;

//    private String qrcodeUrl;

    private UUID createdBy;

    private OffsetDateTime createdAt;
}
