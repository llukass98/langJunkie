package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.nodes.Document;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.JsoupRequest;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import static org.mockito.Mockito.mock;

/**
 * @author Dmitry Lukashevich
 */
public abstract class AbstractDictionaryTest {

    protected Dictionary dictionary;
    protected SearchResult result;
    protected final String word = new StringBuilder("عشق").reverse().toString();
    protected final Request<Document> jsoupRequest = mock(JsoupRequest.class);
}
