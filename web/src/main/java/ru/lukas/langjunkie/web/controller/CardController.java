package ru.lukas.langjunkie.web.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import ru.lukas.langjunkie.web.dto.CreateCardDto;
import ru.lukas.langjunkie.web.service.CardService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dmitry Lukashevich
 */
@Controller
@RequestMapping("/card")
public class CardController {

    private static final String REDIRECT_TO_CARDS = "redirect:/cards";

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/add")
    public String addCard(CreateCardDto createCardDto, @RequestParam String username) throws IOException {
        cardService.saveCard(createCardDto, username);

        return REDIRECT_TO_CARDS;
    }

    @PostMapping("/update")
    public String updateCard(CreateCardDto createCardDto) throws IOException {
        cardService.updateCard(createCardDto);

        return REDIRECT_TO_CARDS;
    }

    @DeleteMapping("/delete/{card-id}")
    public String deleteCard(@PathVariable("card-id") Long id) throws IOException {
        cardService.deleteCard(id);

        return REDIRECT_TO_CARDS;
    }

    @GetMapping("/img/{card-id}")
    public void getCardImage(@PathVariable("card-id") Long cardId, HttpServletResponse response) {
        cardService.addCardImageToResponse(cardId, response);
    }
}
