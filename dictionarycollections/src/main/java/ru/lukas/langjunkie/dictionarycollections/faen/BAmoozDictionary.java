package ru.lukas.langjunkie.dictionarycollections.faen;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.lukas.langjunkie.dictionarycollections.dictionary.Dictionary;
import ru.lukas.langjunkie.dictionarycollections.dictionary.SearchResult;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;

public class BAmoozDictionary extends Dictionary {
	public BAmoozDictionary() {
		language       = "faen";
		link           = "https://dic.b-amooz.com";
		name           = "b-amooz";
	}

	@Override
	public SearchResult search(String word) {
		SearchResult result = new SearchResult();
		ArrayList<String> definitions = new ArrayList<>();

		try {
			word = sanitizeInput(word);
			Document doc = makeRequest(link+"/en/dictionary/rw?word="+ word);

			for (Element element : doc.getElementsByClass("word-row-side")) {
				// if english word has been searched throw NullPointerException
				doc.getElementsByClass("reverse-word-translation-desc")
						.first()
						.text();
				// if no exception continue as usual
				// skip first element
				if (element.child(0).hasClass("py-4")) { continue; }

				for (Element span : element.getElementsByTag("span")) {
					// skip numbers
					if (span.hasClass("reverse-translation-index")) { continue; }
					if (span.hasClass("ml-n2")) { continue; } // skip commas
					// skip spaces and other trash
					if (span.hasClass("text-muted")) { continue; }
					definitions.add(span.text().trim());
				}
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