package ru.lukas.langjunkie.dictionarycollection.faen;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Dictionary;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

public class FarsidictionaryDictionary extends Dictionary {
	public FarsidictionaryDictionary() {
		language       = "faen";
		link           = "https://www.farsidictionary.net";
		dictionaryName = "farsidictionary";
	}

	@Override
	public HashMap<String, Serializable> search(String word) {
		HashMap<String, Serializable> result = new HashMap<>();
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