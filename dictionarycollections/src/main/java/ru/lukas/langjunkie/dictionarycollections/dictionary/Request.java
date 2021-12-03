package ru.lukas.langjunkie.dictionarycollections.dictionary;

import java.io.IOException;

/**
 * @author Dmitry Lukashevich
 */
public interface Request<T> {

    T getRequest(String url) throws IOException;

    T postRequest(String url, String payload) throws IOException;
}
