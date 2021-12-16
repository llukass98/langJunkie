package ru.lukas.langjunkie.web.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.web.api.model.Dictionary;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    List<Dictionary> findByWordAndLanguage(String word, DictionaryCollection language);
}
