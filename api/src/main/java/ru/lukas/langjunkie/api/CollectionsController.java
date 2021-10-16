package ru.lukas.langjunkie.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lukas.langjunkie.dictionarycollection.factory.CollectionFactory;

@RequestMapping("/api/v1.0b")
@RestController
public class CollectionsController {

    @GetMapping("/collections")
    public Collections collections() {
        return new Collections(200, CollectionFactory.getCollections());
    }
}