package ru.lukas.langjunkie.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.xml.crypto.KeySelectorException;
import org.springframework.http.HttpStatus;

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
}
