package com.loopus.loopus_be.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.loopus.loopus_be.model.Users;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
