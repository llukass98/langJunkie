package ru.lukas.langjunkie.web.dto;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.web.model.Word;

/**
 * @author Dmitry Lukashevich
 */
public interface CardViewDto {

    Long getId();

    String getFrontSide();

    String getBackSide();

    DictionaryCollection getLanguage();

    Word getWord();
}
