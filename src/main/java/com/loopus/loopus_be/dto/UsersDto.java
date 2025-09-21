package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UsersDto {
    private UUID userId;

    private String username;

    private String fullName;

    private String avatarUrl;

    private String bio;

    private RoleEnum role = RoleEnum.USER;

    private LocalDate dateOfBirth;

    private LocalDateTime createdAt;
}
