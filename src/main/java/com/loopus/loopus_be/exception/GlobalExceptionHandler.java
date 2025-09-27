package com.loopus.loopus_be.exception;

import com.loopus.loopus_be.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ErrorResponseDto handleLoginException(LoginException ex) {
        return ErrorResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage())
                .build();
    }

    @ExceptionHandler(GroupException.class)
    public ErrorResponseDto groupException(GroupException exception) {
        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
    }


    @ExceptionHandler(FileException.class)
    public ErrorResponseDto fileException(FileException exception) {
        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
    }
}
