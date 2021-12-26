package ru.lukas.langjunkie.web.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.lukas.langjunkie.web.dto.CreateCardDto;
import ru.lukas.langjunkie.web.service.CardService;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/add/card")
    public String addCard(CreateCardDto createCardDto, @RequestParam String username) throws IOException {
        cardService.saveCard(createCardDto, username);

        return "redirect:/cards";
    }

    @PostMapping("/update/card")
    public String updateCard(CreateCardDto createCardDto) throws IOException {
        cardService.updateCard(createCardDto);

        return "redirect:/cards";
    }

    @DeleteMapping("/delete/card/{card-id}")
    public String deleteCard(@PathVariable("card-id") Long id) throws IOException {
        cardService.deleteCard(id);

        return "redirect:/cards";
    }

    @GetMapping("/card/img/{card-id}")
    public void getCardImage(@PathVariable("card-id") Long cardId, HttpServletResponse response) {
        cardService.addCardImageToResponse(cardId, response);
    }
}
