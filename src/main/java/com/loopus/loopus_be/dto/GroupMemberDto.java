package com.loopus.loopus_be.dto;

import com.loopus.loopus_be.enums.RoleEnum;
import com.loopus.loopus_be.model.Group;
import com.loopus.loopus_be.model.GroupMemberId;
import com.loopus.loopus_be.model.Users;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class GroupMemberDto {

    private GroupMemberId id;

    private RoleEnum role;

    private OffsetDateTime joinedAt;

    private UUID userId;
}
