package ru.lukas.langjunkie.api;

import org.springframework.http.HttpStatus;

class ApiError {
    private int status;
    private HttpStatus error;
    private String message;

    public ApiError(int status, HttpStatus error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public HttpStatus getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
