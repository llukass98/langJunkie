package ru.lukas.langjunkie.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import ru.lukas.langjunkie.dictionarycollections.factory.CollectionFactory;
import ru.lukas.langjunkie.web.api.dto.CollectionsDto;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.service.CardService;
import ru.lukas.langjunkie.web.service.UserService;

import java.security.Principal;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class MainController {

    private final UserService userService;
    private final CardService cardService;

    public MainController(UserService userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping(value = "/")
    public String index(ModelMap modelMap, Principal principal) {
        prepareModelMap(modelMap, principal);

        return "index";
    }

    @GetMapping(value = "/cards")
    public String cards(ModelMap modelMap, Principal principal) {
        prepareModelMap(modelMap, principal);

        return "cards";
    }

    @GetMapping(value = "/dictionary")
    public String dictionary(ModelMap modelMap, Principal principal) {
        prepareModelMap(modelMap, principal);

        return "dictionary";
    }

    private void prepareModelMap(ModelMap modelMap, Principal principal) {
        UserDto userDto = userService.getUserByUsernameAsDto(principal.getName());

        modelMap.put("user", userDto);
        modelMap.put("card_number", cardService.getNumberOfCardsByUser(userDto));
        modelMap.put("collections", new CollectionsDto(CollectionFactory.getAllCollections()));
    }
}
