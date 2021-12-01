package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

public class DictionaryFarsiDictionary extends Dictionary {
	public DictionaryFarsiDictionary() {
		language       = "faen";
		link           = "http://www.dictionary-farsi.com";
		name           = "dictionary-farsi";
	}

	@Override
	public SearchResult search(String word) {
		SearchResult result = new SearchResult();
		ArrayList<String> definitions = new ArrayList<>();

		try {
			word = sanitizeInput(word);
			String payload = "submith=ok&text1="
					+URLEncoder.encode(word, "windows-1256")
					+"&submit1=Search&r1=p2e";

			Document doc = makeRequest(link, payload);

			// get definitions
			for (Element element : doc.getElementsByAttribute("onmouseover")) {
				String text = element.text().replace(")", "").trim();
				// skip trash data
				if (text.contains("(")) { continue; }
				if (definitions.contains(text)) { continue; } // skip duplicates
				definitions.add(text);
			}
		} catch (SocketTimeoutException | HttpStatusException ste) {
			// TODO: log the exception
		} catch (IllegalArgumentException iae) {
			// TODO: log the exception
		} catch (NullPointerException npe) {
			// TODO: log the exception
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			result.setLanguage(language);
			result.setName(name);
			result.setLink(link);
			result.setSearchedWord(word);
			result.setResults(definitions);
			result.setExamples(Collections.emptyList());
			result.setSynonyms(Collections.emptyList());
		}

		return result;
	}
}