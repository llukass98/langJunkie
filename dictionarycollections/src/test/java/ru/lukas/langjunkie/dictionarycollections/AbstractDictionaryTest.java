package ru.lukas.langjunkie.dictionarycollections;

import org.jsoup.nodes.Document;

import ru.lukas.langjunkie.dictionarycollections.dictionary.*;

import static org.mockito.Mockito.mock;

/**
 * @author Dmitry Lukashevich
 */
public abstract class AbstractDictionaryTest {

    protected Dictionary dictionary;
    protected SearchResult result;
    protected final String word = new StringBuilder("عشق").reverse().toString();
    protected final Request<Document> jsoupRequest = mock(JsoupRequest.class);
    protected final Request<String> jsonRequest = mock(JsonRequest.class);
}
