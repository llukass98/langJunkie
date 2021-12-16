package ru.lukas.langjunkie.web.api.component;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import ru.lukas.langjunkie.web.api.dto.DictionaryDto;
import ru.lukas.langjunkie.web.api.dto.SearchResultDto;
import ru.lukas.langjunkie.web.api.model.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmitry Lukashevich
 */
@Component
// TODO: consider mapStruct lib
public class DictionaryMapper {

    public DictionaryDto toDto(List<Dictionary> definitions) {
        if (definitions.isEmpty()) { return mapToEmptyDictionaryDto(); }

        return DictionaryDto.builder()
                .status(HttpStatus.SC_OK)
                .collection(definitions.get(0).getLanguage().name())
                .searchedWord(definitions.get(0).getWord())
                .definitions(definitions.stream()
                        .map(definition -> SearchResultDto.builder()
                                .name(definition.getName())
                                .link(definition.getLink())
                                .results(definition.getDefinitions())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public Dictionary toModel(SearchResult searchResult) {
       return Dictionary.builder()
                .language(searchResult.getLanguage())
                .word(searchResult.getSearchedWord())
                .name(searchResult.getName())
                .link(searchResult.getLink())
                .definitions(searchResult.getResults())
                .build();
    }

    private DictionaryDto mapToEmptyDictionaryDto() {
        return DictionaryDto.builder()
                .status(HttpStatus.SC_NOT_FOUND)
                .build();
    }
}
