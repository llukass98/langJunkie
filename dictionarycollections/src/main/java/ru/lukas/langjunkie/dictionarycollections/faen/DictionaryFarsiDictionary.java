package ru.lukas.langjunkie.dictionarycollections.faen;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Request;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Slf4j
public class DictionaryFarsiDictionary extends Dictionary {

	private final Request<Document> documentRequest;

	public DictionaryFarsiDictionary(Request<Document> documentRequest) {
		super("faen", "dictionary-farsi", "http://www.dictionary-farsi.com");
		this.documentRequest = documentRequest;
	}

	@Override
	public SearchResult search(String word) {
		word = sanitizeInput(word);
		List<String> definitions = new ArrayList<>();
		Document document = null;

		try {
			String payload = "submith=ok&text1="
					+ URLEncoder.encode(word, "windows-1256")
					+ "&submit1=Search&r1=p2e";

			document = documentRequest.postRequest(getLink(), payload);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.warn(e.getMessage());
			return SearchResult.builder()
					.language(getLanguage()).name(getName()).link(getLink())
					.results(Collections.emptyList()).build();
		}

		if (document != null) {
			for (Element element : document.getElementsByAttribute("onmouseover")) {
				String text = element.text().replace(")", "").trim();

				if (text.contains("(")) { continue; } // skip trash data
				if (definitions.contains(text)) { continue;} // skip duplicates
				definitions.add(text);
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
