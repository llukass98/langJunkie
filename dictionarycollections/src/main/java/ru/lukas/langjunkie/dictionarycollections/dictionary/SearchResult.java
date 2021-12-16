package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SearchResult {

    private DictionaryCollection language;
    private String name;
    private String link;
    private String searchedWord;
    private List<String> results;
}
