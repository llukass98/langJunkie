package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.Getter;

/**
 * @author Dmitry Lukashevich
 */
@Getter
public abstract class Dictionary {

	private final String language;
	private final String name;
	private final String link;

	public Dictionary(String language, String name, String link) {
		this.language = language;
		this.name = name;
		this.link = link;
	}

	abstract public SearchResult search(String word);

	protected String sanitizeInput(String input) throws IllegalArgumentException {
		input = input.trim();
		if (input.length() == 0) {
			throw new IllegalArgumentException("Searched word is an empty string");
		}
		input = input.replace("\"", "")
				.replace("'", ""); // remove quotes
		// remove unnecessary spaces if input is a phrase
		return input.replaceAll("\\s+", " ");
	}
}
