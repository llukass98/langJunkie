package ru.lukas.langjunkie.web.api.service;

import ru.lukas.langjunkie.web.api.model.Dictionary;

import javax.xml.crypto.KeySelectorException;
import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
public interface DictionaryService {

    List<Dictionary> getDefinitions(String word, String language) throws KeySelectorException;
}
