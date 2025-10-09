package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class GroupAlbumDto {
    private UUID albumId;

    private String name;

    private UsersDto createdBy;

    private OffsetDateTime createdAt = OffsetDateTime.now();
}
