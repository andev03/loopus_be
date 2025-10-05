package com.loopus.loopus_be.dto.request;

import com.loopus.loopus_be.enums.EventRepeat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateEventRequest {
    @NotNull(message = "Vui lòng nhập group id")
    private UUID eventId;

    @NotBlank(message = "Vui lòng nhập title")
    private String title;

    @NotBlank(message = "Vui lòng nhập description")
    private String description;

    @NotNull(message = "Vui lòng nhập eventDate")
    private LocalDate eventDate;

    @NotNull(message = "Vui lòng nhập eventTime")
    private LocalTime eventTime;

    @NotNull(message = "Vui lòng nhập repeatType")
    private EventRepeat repeatType;
}
