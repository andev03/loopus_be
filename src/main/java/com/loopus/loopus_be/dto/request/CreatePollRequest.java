package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreatePollRequest {

    @NotNull(message = "Vui lòng nhập groupId!")
    private UUID groupId;

    @NotNull(message = "Vui lòng nhập userId!")
    private UUID userId;

    @NotNull(message = "Vui lòng nhập tên poll!")
    private String name;

    @NotNull(message = "Vui lòng nhập options!")
    private List<String> options;
}
