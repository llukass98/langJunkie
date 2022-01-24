package ru.lukas.langjunkie.web.api.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.lukas.langjunkie.web.api.dto.CollectionsDto;
import ru.lukas.langjunkie.dictionarycollections.factory.CollectionFactory;

/**
 * @author Dmitry Lukashevich
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0b")
public class CollectionsController {

    @GetMapping("/collections")
    public CollectionsDto collections() {
        return new CollectionsDto(CollectionFactory.getAvailableCollections());
    }
}
