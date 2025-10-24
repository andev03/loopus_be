package com.loopus.loopus_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UpdateUserRequest {
    @NotNull(message = "Vui lòng nhập userId!")
    private UUID userId;

    @NotBlank(message = "Vui lòng nhập họ!")
    private String firstName;

    @NotBlank(message = "Vui lòng nhập tên!")
    private String lastName;

    @NotNull(message = "Vui lòng nhập ngày sinh!")
    private LocalDate dob;

    @NotBlank(message = "Vui lòng nhập bio!")
    private String bio;

    @NotBlank(message = "Vui lòng nhập email!")
    private String email;

    @NotBlank(message = "Vui lòng nhập bankNumber!")
    private String bankNumber;

    @NotNull(message = "Vui lòng nhập bankId!")
    private UUID bankId;
}
