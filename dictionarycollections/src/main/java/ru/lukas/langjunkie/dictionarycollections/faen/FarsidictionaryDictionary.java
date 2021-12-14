package ru.lukas.langjunkie.dictionarycollections.faen;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
@Slf4j
public class FarsidictionaryDictionary extends Dictionary {

	private final Request<Document> documentRequest;

	public FarsidictionaryDictionary(Request<Document> documentRequest) {
		super("faen", "farsidictionary", "https://www.farsidictionary.net");
		this.documentRequest = documentRequest;
	}

	@Override
	public SearchResult search(String word) {
		word = sanitizeInput(word);
		List<String> definitions = new ArrayList<>();
		String requestUrl = getLink() + "/index.php?q=" + word;
		Document document;
		Elements elems = null;

		try {
			document = documentRequest.getRequest(requestUrl);
			Element elements = document.getElementById("faen");

			if (elements != null) {
				elems = elements.getElementsByAttributeValue("align", "left");
			}

			if (elems != null) {
				elems.remove(0);
				elems.remove(0);

				for (Element element : elems) { definitions.add(element.text().trim()); }
			}
		} catch (IOException e) {
			log.warn(e.getMessage());
			return SearchResult.builder()
					.language(getLanguage()).name(getName()).link(getLink())
					.results(Collections.emptyList()).build();
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