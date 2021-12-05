package ru.lukas.langjunkie.web.component;

import org.mapstruct.Mapper;
import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.dto.WordDto;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.model.Word;

/**
 * @author Dmitry Lukashevich
 */
@Mapper(componentModel = "spring")
public interface UserCardWordMapper {

    UserDto toUserDto(User entity);

    User toUserModel(UserDto dto);

    CardDto toCardDto(Card entity);

    Card toCardModel(CardDto dto);

    WordDto toWordDto(Word entity);

    Word toWordModel(WordDto dto);
}
