package ru.lukas.langjunkie.web.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.lukas.langjunkie.web.api.component.DictionaryMapper;
import ru.lukas.langjunkie.web.api.dto.DictionaryDto;
import ru.lukas.langjunkie.web.api.service.DictionaryService;

import javax.xml.crypto.KeySelectorException;

@RestController
@RequestMapping("/api/v1.0b")
public class DictionaryController {

	private final DictionaryService dictionaryService;
	private final DictionaryMapper dictionaryMapper;

	public DictionaryController(DictionaryService dictionaryService, DictionaryMapper dictionaryMapper) {
		this.dictionaryService = dictionaryService;
		this.dictionaryMapper = dictionaryMapper;
	}

	@GetMapping("/definitions")
	public DictionaryDto definitions (@RequestParam String word, @RequestParam String lang)
			throws KeySelectorException
	{
		return dictionaryMapper.toDto(dictionaryService.getDefinitions(word, lang));
	}
}