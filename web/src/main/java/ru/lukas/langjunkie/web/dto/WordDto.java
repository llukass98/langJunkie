package ru.lukas.langjunkie.web.dto;

import lombok.*;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class WordDto {

    private String word;
    private List<CardDto> cards;
}
