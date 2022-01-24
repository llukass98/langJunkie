package ru.lukas.langjunkie.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.lukas.langjunkie.web.dto.CardViewDto;
import ru.lukas.langjunkie.web.dto.CreateCardDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dmitry Lukashevich
 */
public interface CardService {

    void saveCard(CreateCardDto createCardDto, String username) throws IOException;

    void updateCard(CreateCardDto createCardDto) throws IOException;

    void deleteCard(Long id) throws IOException;

    void addCardImageToResponse(Long cardId, HttpServletResponse response);

    Page<CardViewDto> getAllCardViewByUserId(Long id, Pageable pageable);
}
