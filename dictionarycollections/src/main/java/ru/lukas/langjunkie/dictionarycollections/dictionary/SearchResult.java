package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Getter
@Setter
public class SearchResult {

    private String language;
    private String name;
    private String link;
    private String searchedWord;
    private List<String> results;
    private List<String> examples;
    private List<String> synonyms;
}
