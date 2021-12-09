package ru.lukas.langjunkie.web.service;

import ru.lukas.langjunkie.web.dto.CardDto;

/**
 * @author Dmitry Lukashevich
 */
public interface CardService {

    void saveCard(CardDto cardDto, String username);

    void updateCard(CardDto cardDto);

    void deleteCard(Long id);
}
