package ru.lukas.langjunkie.dictionarycollection.faen;

import java.util.HashMap;
import java.util.ArrayList;
import java.net.SocketTimeoutException;
import java.io.Serializable;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Dictionary;

public class BAmoozDictionary extends Dictionary {
	public BAmoozDictionary() {
		language       = "faen";
		link           = "https://dic.b-amooz.com";
		dictionaryName = "b-amooz";
	}

	public HashMap<String, Serializable> search(String word) {
		HashMap<String, Serializable> result = new HashMap<>();
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
			result.put("language",      language);
			result.put("name",          dictionaryName);
			result.put("link",          link);
			result.put("searched_word", word);
			result.put("results",       definitions);
			result.put("examples",      new ArrayList<String>());
			result.put("synonyms",      new ArrayList<String>());
		}

		return result;
	}
}
