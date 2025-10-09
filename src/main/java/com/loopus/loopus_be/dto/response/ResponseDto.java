package com.loopus.loopus_be.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseDto<T> {

    private int status;

    private String message;

    private T data;
}
