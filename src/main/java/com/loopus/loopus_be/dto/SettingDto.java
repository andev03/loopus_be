package com.loopus.loopus_be.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingDto {

    private UUID id;

    private String type;

    private Boolean enabled;

    private String description;
}
