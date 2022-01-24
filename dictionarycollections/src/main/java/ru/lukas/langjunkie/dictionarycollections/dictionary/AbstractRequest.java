package ru.lukas.langjunkie.dictionarycollections.dictionary;

/**
 * @author Dmitry Lukashevich
 */
public abstract class AbstractRequest<T> implements Request<T> {

    @Override
    public T getRequest(String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T postRequest(String url, String payload) {
        throw new UnsupportedOperationException();
    }
}
