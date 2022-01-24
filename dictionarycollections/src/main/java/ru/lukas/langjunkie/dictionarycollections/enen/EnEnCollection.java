package ru.lukas.langjunkie.dictionarycollections.enen;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Collection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.dictionarycollections.dictionary.JsonRequest;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;

import java.util.List;

/**
 * English to English monolingual dictionary collection
 *
 * @author Dmitry Lukashevich
 */
public class EnEnCollection extends Collection {

    private static final Request<String> jsonRequest = new JsonRequest();

    public EnEnCollection() {
        super(DictionaryCollection.ENEN, List.of(
                new MerriamWebsterDictionary(jsonRequest)
        ));
    }
}