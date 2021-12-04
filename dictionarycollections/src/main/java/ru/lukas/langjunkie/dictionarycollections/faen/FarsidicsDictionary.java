package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.nodes.Document;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
public class FarsidicsDictionary extends Dictionary {

	private final Request<Document> documentRequest;

	private static final List<Character> persianLetters = List.of(
			'ء', 'ا', 'آ', 'ب', 'پ', 'ت', 'ث', 'ج', 'چ',
			'ح', 'خ', 'د', 'ذ', 'ر', 'ز', 'ژ', 'س', 'ش',
			'ص', 'ض', 'ط', 'ظ', 'ع', 'غ', 'ف', 'ق', 'ك',
			'ک', 'گ', 'ل', 'م', 'ن', 'و', 'ه', 'ي', 'ی'
	);

	public FarsidicsDictionary(Request<Document> documentRequest) {
		super("faen", "farsidics", "http://www.farsidics.com");
		this.documentRequest = documentRequest;
	}

	@Override
	public SearchResult search(String word) {
		word = sanitizeInput(word);
		List<String> definitions = new ArrayList<>();
		String[] blockOfDefinitions;
		String requestUrl = getLink() + "/ajax-searchf.php?keyword=" + word;
		Document document = null;

		try {
			document = documentRequest.getRequest(requestUrl);
		} catch (Exception e) {
			e.printStackTrace(); // TODO: add logger
			return SearchResult.builder()
					.language(getLanguage()).name(getName()).link(getLink())
					.results(Collections.emptyList()).build();
		}

		String text  = null;
		if (document != null) {
			text = document.body().text().replace(" _", "");
		}

		if (text != null && !text.contains("No Results")) {
			blockOfDefinitions = text.split(" ");

			for (int i = 1; i < blockOfDefinitions.length - 1; i++) {
				// persian letters are in second word
				// break the loop, there's no definition, results array is empty
				if (persianLetters.contains(blockOfDefinitions[i].charAt(0))) {
					break;
				}
				if (blockOfDefinitions[i].contains("(")) {
					continue;
				} // skip trash data, if any
				if (definitions.contains(blockOfDefinitions[i]
						.trim()
						.replace(",", ""))) {
					continue;
				} // skip duplicates

				// find the last element and break the loop
				if (!blockOfDefinitions[i].contains(",")) {
					definitions.add(blockOfDefinitions[i].trim());
					break;
				}

				definitions.add(blockOfDefinitions[i].trim().replace(",", ""));
			}
		}

		return SearchResult.builder()
				.language(getLanguage())
				.name(getName())
				.link(getLink())
				.searchedWord(word)
				.results(definitions)
				.build();
	}
}