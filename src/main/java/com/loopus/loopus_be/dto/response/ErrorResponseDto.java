package com.loopus.loopus_be.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponseDto {

    private int status;

    private String message;
}
