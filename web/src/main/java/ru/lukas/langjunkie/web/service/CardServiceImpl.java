package ru.lukas.langjunkie.web.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.lukas.langjunkie.web.component.CardMapper;
import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.repository.CardRepository;
import ru.lukas.langjunkie.web.repository.UserRepository;

/**
 * @author Dmitry Lukashevich
 */
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Override
    public void saveCard(CardDto cardDto, String username) {
        User user = userRepository.findByUsername(username);
        Card card = cardMapper.toCardModel(cardDto);

        card.setUser(user);
        user.getCards().add(card);

        userRepository.save(user);
    }

    @Override
    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(card.getUser().getUsername());

        user.getCards().remove(card);

        userRepository.save(user);
    }

    @Override
    public void updateCard(CardDto cardDto) {
        Card card = cardRepository.findById(cardDto.getId()).orElseThrow();

        card.setLanguage(cardDto.getLanguage());
        card.setFrontSide(cardDto.getFrontSide());
        card.setBackSide(cardDto.getBackSide());
        card.getWord().setWord(cardDto.getWord());

        cardRepository.save(card);
    }
}
