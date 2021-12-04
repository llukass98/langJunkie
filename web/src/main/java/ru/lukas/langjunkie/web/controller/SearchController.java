package ru.lukas.langjunkie.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import ru.lukas.langjunkie.dictionarycollections.factory.CollectionFactory;
import ru.lukas.langjunkie.web.api.dto.CollectionsDto;

/**
 * @author Dmitry Lukashevich
 */
@Controller
public class SearchController {

    @GetMapping(value = "/")
    public String index(ModelMap modelMap) {
        modelMap.put("collections", new CollectionsDto(CollectionFactory.getCollections()));

        return "index";
    }
}
