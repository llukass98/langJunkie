package ru.lukas.langjunkie.web.component;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.ImageFileInfo;
import ru.lukas.langjunkie.web.model.Word;

/**
 * @author Dmitry Lukashevich
 */
@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mappings({
            @Mapping(source = "entity.word.word", target = "word"),
            @Mapping(
                    source = "entity.image",
                    target = "picturePath",
                    qualifiedByName = "imageFileInfoToPicturePath"
            )
    })
    CardDto toCardDto(Card entity);

    @Mapping(source = "dto.word", target = "word", qualifiedByName = "stringToWord")
    Card toCardModel(CardDto dto);

    @Named("stringToWord")
    default Word stringToWord(String word) {
        Word word1 = new Word();
        word1.setWord(word);

        return word1;
    }

    @Named("imageFileInfoToPicturePath")
    default String imageFileInfoToPicturePath(ImageFileInfo imageFileInfo) {
        return imageFileInfo == null ? null : imageFileInfo.getFilename();
    }
}