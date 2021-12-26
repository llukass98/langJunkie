package ru.lukas.langjunkie.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;

import java.util.Set;

/**
 * @author Dmitry Lukashevich
 */
@Data
@AllArgsConstructor
public class CollectionsDto {

    private final Set<DictionaryCollection> collections;
}