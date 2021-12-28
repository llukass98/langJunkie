package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.Getter;

import java.util.regex.Pattern;

/**
 * @author Dmitry Lukashevich
 */
@Getter
public abstract class Dictionary {

	private final DictionaryCollection language;
	private final String name;
	private final String link;

	private static final Pattern pattern = Pattern.compile("[\\\\\\[\\]{}?!.,\"'()؟،]");

	protected Dictionary(DictionaryCollection language, String name, String link) {
		this.language = language;
		this.name = name;
		this.link = link;
	}

	public abstract SearchResult search(String word);

	protected String sanitizeInput(String input) throws IllegalArgumentException {
		input = input.trim();
		if (input.length() == 0) {
			throw new IllegalArgumentException("Searched word is an empty string");
		}

		// remove all undesired characters
		input = pattern.matcher(input).replaceAll("");
		// replace two ot more spaces with one
		return input.replaceAll("\\s+", " ");
	}
}
