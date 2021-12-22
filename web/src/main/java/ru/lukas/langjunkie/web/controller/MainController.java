package ru.lukas.langjunkie.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import ru.lukas.langjunkie.dictionarycollections.factory.CollectionFactory;
import ru.lukas.langjunkie.web.api.dto.CollectionsDto;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.service.UserService;

import java.security.Principal;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(ModelMap modelMap, Principal principal) {
        prepareModelMap(modelMap, principal);

        return "index";
    }

    @GetMapping("/cards")
    public String cards(ModelMap modelMap, Principal principal) {
        prepareModelMap(modelMap, principal);

        return "cards";
    }

    @GetMapping("/dictionary")
    public String dictionary(ModelMap modelMap, Principal principal) {
        prepareModelMap(modelMap, principal);

        return "dictionary";
    }

    private void prepareModelMap(ModelMap modelMap, Principal principal) {
        UserDto userDto = userService.getUserByUsernameAsDto(principal.getName());

        modelMap.put("user", userDto);
        modelMap.put("collections", new CollectionsDto(CollectionFactory.getAvailableCollections()));
    }
}
