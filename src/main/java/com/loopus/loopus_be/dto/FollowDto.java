package com.loopus.loopus_be.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowDto {
    private UUID id;

    private UsersDto follower;

    private UsersDto following;

    private Instant createdAt;
}
