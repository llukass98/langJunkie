package ru.lukas.langjunkie.web.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.service.CardService;
import ru.lukas.langjunkie.web.service.CardServiceImpl;

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

    @PostMapping(value = "/add/card")
    public String addCard(CardDto cardDto, @RequestParam String username) throws IOException {
        cardService.saveCard(cardDto, username);

        return "redirect:/cards";
    }

    @PostMapping(value = "/update/card/{card-id}")
    public String updateCard(CardDto cardDto, @PathVariable("card-id") Long id)
            throws IOException
    {
        cardDto.setId(id);
        cardService.updateCard(cardDto);

        return "redirect:/cards";
    }

    @GetMapping("/delete/card/{card-id}")
    public String deleteCard(@PathVariable("card-id") Long id) throws IOException {
        cardService.deleteCard(id);

        return "redirect:/cards";
    }
}
