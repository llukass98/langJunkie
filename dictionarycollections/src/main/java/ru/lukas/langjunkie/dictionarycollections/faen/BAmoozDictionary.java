package ru.lukas.langjunkie.dictionarycollections.faen;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Slf4j
public class BAmoozDictionary extends Dictionary {

	private final Request<Document> documentRequest;

	public BAmoozDictionary(Request<Document> documentRequest) {
		super("faen", "b-amooz", "https://dic.b-amooz.com");
		this.documentRequest = documentRequest;
	}

	@Override
	public SearchResult search(String word) {
		word = sanitizeInput(word);
		List<String> definitions = new ArrayList<>();
		String requestUrl = getLink() + "/en/dictionary/rw?word=" + word;
		Document document;

		try {
			document = documentRequest.getRequest(requestUrl);
		} catch (IOException e) {
			log.warn(e.getMessage());
			return SearchResult.builder()
					.language(getLanguage()).name(getName()).link(getLink())
					.results(Collections.emptyList()).build();
		}

		if (document != null) {
			for (Element element : document.getElementsByClass("word-row-side")) {
				// if english word has been searched throw NullPointerException
				// TODO: don't like it here, consider for refactoring
				document.getElementsByClass("reverse-word-translation-desc").first().text();
				// if no exception continue as usual
				// skip first element
				if (element.child(0).hasClass("py-4")) { continue; }

				for (Element span : element.getElementsByTag("span")) {
					if (span.hasClass("reverse-translation-index")) { continue; } // skip numbers
					if (span.hasClass("ml-n2")) { continue; } // skip commas
					if (span.hasClass("text-muted")) { continue; } // skip spaces and other trash
					definitions.add(span.text().trim());
				}
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