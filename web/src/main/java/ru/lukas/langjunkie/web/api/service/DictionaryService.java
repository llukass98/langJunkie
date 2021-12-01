package ru.lukas.langjunkie.web.api.service;

import org.springframework.stereotype.Service;

import ru.lukas.langjunkie.web.api.component.DictionaryMapper;
import ru.lukas.langjunkie.web.api.model.Dictionary;
import ru.lukas.langjunkie.web.api.repository.DictionaryRepository;
import ru.lukas.langjunkie.dictionarycollections.factory.CollectionFactory;

import javax.xml.crypto.KeySelectorException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmitry Lukashevich
 */
@Service
public class DictionaryService {

    private final DictionaryMapper dictionaryMapper;
    private final DictionaryRepository hibernateDictionaryRepository;

    public DictionaryService(DictionaryMapper dictionaryMapper, DictionaryRepository hibernateDictionaryRepository) {
        this.dictionaryMapper = dictionaryMapper;
        this.hibernateDictionaryRepository = hibernateDictionaryRepository;
    }

    public List<Dictionary> getDefinitions(String word, String language) throws KeySelectorException {
        word = word.trim();
        List<Dictionary> definitionsFromDB =
                hibernateDictionaryRepository.findByWordAndLanguage(word, language);

        return definitionsFromDB.isEmpty() ?
                saveAndGetDefinitions(word, language) : definitionsFromDB;
    }

    private List<Dictionary> saveAndGetDefinitions(String word, String language) throws KeySelectorException {
        List<Dictionary> definitions = CollectionFactory.getCollection(language).search(word)
                .stream().map(dictionaryMapper::toModel).collect(Collectors.toList());

        hibernateDictionaryRepository.saveAll(definitions);

        return definitions;
    }
}
