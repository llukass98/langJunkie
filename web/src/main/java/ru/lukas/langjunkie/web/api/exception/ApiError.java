package ru.lukas.langjunkie.web.api.exception;

import lombok.Data;

import org.springframework.http.HttpStatus;

/**
 * @author Dmitry Lukashevich
 */
@Data
class ApiError {

    private final int status;
    private final HttpStatus error;
    private final String message;
}