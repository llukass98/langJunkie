package ru.lukas.langjunkie.web.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Data
@Builder
public class DictionaryDto {

    private final Integer status;
    private final String collection;

    @JsonProperty("searched_word")
    private final String searchedWord;

    private final List<SearchResultDto> definitions;
}