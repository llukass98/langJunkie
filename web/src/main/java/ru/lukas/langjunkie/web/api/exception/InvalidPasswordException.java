package ru.lukas.langjunkie.web.api.exception;

/**
 * @author Dmitry Lukashevich
 */
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
