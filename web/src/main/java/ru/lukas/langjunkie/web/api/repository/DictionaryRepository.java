package ru.lukas.langjunkie.web.api.repository;

import ru.lukas.langjunkie.web.api.model.Dictionary;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
public interface DictionaryRepository {

    List<Dictionary> findByWordAndLanguage(String word, String language);

    void saveAll(List<Dictionary> dictionaries);
}
