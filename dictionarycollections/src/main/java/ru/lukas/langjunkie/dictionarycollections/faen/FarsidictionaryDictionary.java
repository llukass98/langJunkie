package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;

public class FarsidictionaryDictionary extends Dictionary {
	public FarsidictionaryDictionary() {
		language       = "faen";
		link           = "https://www.farsidictionary.net";
		name           = "farsidictionary";
	}

	@Override
	public SearchResult search(String word) {
		SearchResult result = new SearchResult();
		ArrayList<String> definitions = new ArrayList<>();

		try {
			word = sanitizeInput(word);
			Document doc   = makeRequest(link+"/index.php?q="+ word);
			Elements elems = doc.getElementById("faen")
					.getElementsByAttributeValue("align", "left");

			elems.remove(0);
			elems.remove(0);

			for (Element element : elems) {
				definitions.add(element.text().trim());
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