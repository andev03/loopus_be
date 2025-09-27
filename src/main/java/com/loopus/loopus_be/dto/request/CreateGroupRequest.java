package com.loopus.loopus_be.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateGroupRequest {

    private UUID createdBy;
    private String name;
    private String description;
    private String avatarUrl;
    private List<UUID> userMemberIds;
}
