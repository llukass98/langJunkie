package ru.lukas.langjunkie.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import ru.lukas.langjunkie.dictionarycollections.factory.CollectionFactory;
import ru.lukas.langjunkie.web.api.dto.CollectionsDto;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.service.UserServiceImpl;

import java.security.Principal;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class MainController {

    private final UserServiceImpl userServiceImpl;

    public MainController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/")
    public String index(ModelMap modelMap, Principal principal) {
        UserDto userDto = userServiceImpl.getUserByUsername(principal.getName());

        modelMap.put("user", userDto);
        modelMap.put("collections", new CollectionsDto(CollectionFactory.getCollections()));

        return "index";
    }

    @GetMapping(value = "/cards")
    public String cards(ModelMap modelMap, Principal principal) {
        UserDto userDto = userServiceImpl.getUserByUsername(principal.getName());

        modelMap.put("user", userDto);
        modelMap.put("collections", new CollectionsDto(CollectionFactory.getCollections()));

        return "cards";
    }

    @GetMapping(value = "/dictionary")
    public String dictionary(ModelMap modelMap, Principal principal) {
        UserDto userDto = userServiceImpl.getUserByUsername(principal.getName());

        modelMap.put("user", userDto);
        modelMap.put("collections", new CollectionsDto(CollectionFactory.getCollections()));

        return "dictionary";
    }
}
