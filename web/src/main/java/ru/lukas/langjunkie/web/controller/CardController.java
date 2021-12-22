package ru.lukas.langjunkie.web.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.service.CardServiceImpl;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class CardController {

    private final CardServiceImpl cardServiceImpl;

    public CardController(CardServiceImpl cardServiceImpl) {
        this.cardServiceImpl = cardServiceImpl;
    }

    @PostMapping("/add/card")
    public String addCard(CardDto cardDto, @RequestParam String username) throws IOException {
        cardServiceImpl.saveCard(cardDto, username);

        return "redirect:/cards";
    }

    @PostMapping("/update/card/{card-id}")
    public String updateCard(CardDto cardDto, @PathVariable("card-id") Long id)
            throws IOException
    {
        cardDto.setId(id);
        cardServiceImpl.updateCard(cardDto);

        return "redirect:/cards";
    }

    @DeleteMapping("/delete/card/{card-id}")
    public String deleteCard(@PathVariable("card-id") Long id) throws IOException {
        cardServiceImpl.deleteCard(id);

        return "redirect:/cards";
    }

    @GetMapping("/img/uploaded/{card-id}")
    public void getCardImage(@PathVariable("card-id") Long cardId,
                             HttpServletResponse response)
    {
        cardServiceImpl.addCardImageToResponse(cardId, response);
    }
}
