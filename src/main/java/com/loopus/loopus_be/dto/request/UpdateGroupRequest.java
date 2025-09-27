package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateGroupRequest {
    @NotBlank(message = "Vui lòng nhập groupId!")
    private UUID groupId;

    @NotBlank(message = "Vui lòng nhập tên nhóm!")
    private String groupName;

    @NotBlank(message = "Vui lòng nhập mô tả nhóm!")
    private String description;
}
