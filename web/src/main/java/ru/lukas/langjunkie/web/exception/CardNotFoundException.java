package ru.lukas.langjunkie.web.exception;

/**
 * @author Dmitry Lukashevich
 */
public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(String message) {
        super(message);
    }
}
