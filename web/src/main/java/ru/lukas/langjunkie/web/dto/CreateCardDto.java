package ru.lukas.langjunkie.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;

/**
 * @author Dmitry Lukashevich
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "frontSide", "backSide", "language", "word" })
@Builder
public class CreateCardDto {

    private Long id;
    private String frontSide;
    private String backSide;
    private DictionaryCollection language;
    private String word;
    private MultipartFile picture;
}
