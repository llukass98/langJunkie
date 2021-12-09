package ru.lukas.langjunkie.web.component;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.Word;

/**
 * @author Dmitry Lukashevich
 */
@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(source = "entity.word.word", target = "word")
    CardDto toCardDto(Card entity);

    @Mapping(source = "dto.word", target = "word", qualifiedByName = "stringToWord")
    Card toCardModel(CardDto dto);

    @Named("stringToWord")
    default Word stringToWord(String word) {
        Word word1 = new Word();
        word1.setWord(word);

        return word1;
    }
}
