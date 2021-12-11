package ru.lukas.langjunkie.web.service;

import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.dto.UserDto;

import java.io.IOException;

/**
 * @author Dmitry Lukashevich
 */
public interface CardService {

    void saveCard(CardDto cardDto, String username) throws IOException;

    void updateCard(CardDto cardDto) throws IOException;

    void deleteCard(Long id) throws IOException;

    Long getNumberOfCardsByUser(UserDto userDto);
}
