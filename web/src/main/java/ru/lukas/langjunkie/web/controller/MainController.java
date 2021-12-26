package ru.lukas.langjunkie.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.lukas.langjunkie.dictionarycollections.factory.CollectionFactory;
import ru.lukas.langjunkie.web.api.dto.CollectionsDto;
import ru.lukas.langjunkie.web.dto.CardViewDto;
import ru.lukas.langjunkie.web.dto.UserViewDto;
import ru.lukas.langjunkie.web.service.CardService;
import ru.lukas.langjunkie.web.service.UserService;

import java.security.Principal;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class MainController {

    private final static Integer DEFAULT_PAGE_SIZE = 50;

    private final UserService userService;
    private final CardService cardService;

    public MainController(UserService userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping("/")
    public String getIndex(ModelMap modelMap, Principal principal) {
        prepareModelMap(modelMap, principal.getName());

        return "index";
    }

    @GetMapping("/cards")
    public String getCards(ModelMap modelMap,
                        Principal principal,
                        @RequestParam(defaultValue = "0") Integer page)
    {
        prepareModelMap(modelMap, principal.getName());
        Page<CardViewDto> cards = cardService.getAllCardViewByUserId(
                ((UserViewDto) modelMap.getAttribute("user")).getId(),
                PageRequest.of(page, DEFAULT_PAGE_SIZE)
        );

        modelMap.put("cards", cards);

        return "cards";
    }

    @GetMapping("/dictionary")
    public String getDictionary(ModelMap modelMap, Principal principal) {
        prepareModelMap(modelMap, principal.getName());

        return "dictionary";
    }

    private void prepareModelMap(ModelMap modelMap, String username) {
        modelMap.put("user", userService.getUserViewByUsername(username));
        modelMap.put("collections", new CollectionsDto(CollectionFactory.getAvailableCollections()));
    }
}
