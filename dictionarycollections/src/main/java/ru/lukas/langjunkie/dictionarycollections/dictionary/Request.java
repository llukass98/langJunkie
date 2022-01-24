package ru.lukas.langjunkie.dictionarycollections.dictionary;

/**
 * @author Dmitry Lukashevich
 */
public interface Request<T> {

    T getRequest(String url);

    T postRequest(String url, String payload);
}
