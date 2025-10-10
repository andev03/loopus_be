package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAlbumRequest {

    @NotNull(message = "Vui lòng nhập groupId")
    private UUID groupId;

    @NotNull(message = "Vui lòng nhập tên album")
    private String name;

    @NotNull(message = "Vui lòng nhập createdBy")
    private UUID createdBy;
}
