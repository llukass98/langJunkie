package ru.lukas.langjunkie.web.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Data
@Builder
public class SearchResultDto {

    private final String name;
    private final String link;
    private final List<String> results;
}
