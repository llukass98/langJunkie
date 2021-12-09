package ru.lukas.langjunkie.web.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.lukas.langjunkie.web.api.component.DictionaryMapper;
import ru.lukas.langjunkie.web.api.dto.DictionaryDto;
import ru.lukas.langjunkie.web.api.service.DictionaryServiceImpl;

import javax.xml.crypto.KeySelectorException;

@RestController
@RequestMapping("/api/v1.0b")
public class DictionaryController {

	private final DictionaryServiceImpl dictionaryServiceImpl;
	private final DictionaryMapper dictionaryMapper;

	public DictionaryController(DictionaryServiceImpl dictionaryServiceImpl, DictionaryMapper dictionaryMapper) {
		this.dictionaryServiceImpl = dictionaryServiceImpl;
		this.dictionaryMapper = dictionaryMapper;
	}

	@GetMapping("/definitions")
	public DictionaryDto definitions (@RequestParam String word, @RequestParam String lang)
			throws KeySelectorException
	{
		return dictionaryMapper.toDto(dictionaryServiceImpl.getDefinitions(word, lang));
	}
}