package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateGroupRequest {

    @NotNull(message = "Vui lòng nhập user id")
    private UUID createdBy;

    @NotBlank(message = "Vui lòng nhập name")
    private String name;

    @NotBlank(message = "Vui lòng nhập description")
    private String description;

    @NotBlank(message = "Vui lòng nhập avatarUrl")
    private String avatarUrl;

    @NotNull(message = "Vui lòng nhập userMemberIds")
    private List<UUID> userMemberIds;
}
