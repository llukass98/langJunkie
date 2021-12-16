package ru.lukas.langjunkie.web.api.service;

import org.springframework.stereotype.Service;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.web.api.component.DictionaryMapper;
import ru.lukas.langjunkie.web.api.model.Dictionary;
import ru.lukas.langjunkie.web.api.repository.DictionaryRepository;
import ru.lukas.langjunkie.dictionarycollections.factory.CollectionFactory;

import javax.xml.crypto.KeySelectorException;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author Dmitry Lukashevich
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryMapper dictionaryMapper;
    private final DictionaryRepository dictionaryRepository;

    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper, DictionaryRepository dictionaryRepository) {
        this.dictionaryMapper = dictionaryMapper;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public List<Dictionary> getDefinitions(String word, DictionaryCollection collection)
            throws KeySelectorException
    {
        word = word.trim();
        List<Dictionary> definitionsFromDB =
                dictionaryRepository.findByWordAndLanguage(word, collection);

        return definitionsFromDB.isEmpty() ?
                saveAndGetDefinitions(word, collection) : definitionsFromDB;
    }

    private List<Dictionary> saveAndGetDefinitions(String word, DictionaryCollection collection)
            throws KeySelectorException
    {
        List<Dictionary> definitions = CollectionFactory
                .getCollection(collection).search(word)
                .stream().map(dictionaryMapper::toModel).collect(Collectors.toList());

        Executors.newSingleThreadExecutor().submit(() -> dictionaryRepository.saveAll(definitions));

        return definitions;
    }
}
