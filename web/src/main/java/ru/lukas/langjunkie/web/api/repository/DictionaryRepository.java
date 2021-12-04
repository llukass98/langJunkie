package ru.lukas.langjunkie.web.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lukas.langjunkie.web.api.model.Dictionary;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
public interface DictionaryRepository {

    void saveAll(List<Dictionary> dictionaries);

    List<Dictionary> findByWordAndLanguage(String word, String language);
}
