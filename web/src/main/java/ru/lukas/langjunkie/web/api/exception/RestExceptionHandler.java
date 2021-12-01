package ru.lukas.langjunkie.web.api.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.crypto.KeySelectorException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(KeySelectorException.class)
    protected ResponseEntity<ApiError> handleKeySelector(KeySelectorException e) {
        ApiError apiError = new ApiError(404,
                HttpStatus.NOT_FOUND,
                e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getError());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ApiError> handleKeySelector(RuntimeException e) {
        ApiError apiError = new ApiError(404,
                HttpStatus.NOT_FOUND,
                e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getError());
    }
}