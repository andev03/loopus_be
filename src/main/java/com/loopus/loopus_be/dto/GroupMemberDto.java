package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class GroupMemberDto {
    private UsersDto user;

    private RoleEnum role = RoleEnum.MEMBER;

    private OffsetDateTime joinedAt;
}
