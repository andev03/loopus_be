package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Vui lòng nhập username!")
    private String username;

    @NotBlank(message = "Vui lòng nhập password!")
    private String password;
}
