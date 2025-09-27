package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Vui lòng nhập họ!")
    private String firstName;

    @NotBlank(message = "Vui lòng nhập tên!")
    private String lastName;

    @NotNull(message = "Vui lòng nhập ngày sinh!")
    private LocalDate dob;

    @NotBlank(message = "Vui lòng nhập email!")
    private String email;

    @NotBlank(message = "Vui lòng nhập password!")
    private String password;
}
