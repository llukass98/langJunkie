package ru.lukas.langjunkie.web.api.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;
import ru.lukas.langjunkie.web.api.dto.DictionaryDto;
import ru.lukas.langjunkie.web.api.service.DictionaryService;

import javax.xml.crypto.KeySelectorException;

/**
 * @author Dmitry Lukashevich
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0b")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/definitions")
    public DictionaryDto definitions (@RequestParam String word, @RequestParam DictionaryCollection lang)
            throws KeySelectorException
    {
        return dictionaryService.getDefinitions(word, lang);
    }
}