package ru.lukas.langjunkie.web.api.service;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.web.api.dto.DictionaryDto;

import javax.xml.crypto.KeySelectorException;

/**
 * @author Dmitry Lukashevich
 */
public interface DictionaryService {

    DictionaryDto getDefinitions(String word, DictionaryCollection collection) throws KeySelectorException;
}
