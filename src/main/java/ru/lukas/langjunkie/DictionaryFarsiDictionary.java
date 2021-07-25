package ru.lukas.langjunkie;

import java.util.HashMap;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.net.SocketTimeoutException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class DictionaryFarsiDictionary extends Dictionary {
    public DictionaryFarsiDictionary() {
	language       = "faen";
	link           = "http://www.dictionary-farsi.com";
	dictionaryName = "dictionary-farsi";
    }

    public HashMap search(String word) {
	HashMap result = new HashMap();
	ArrayList<String> definitions = new ArrayList<String>();

	word = word.replace("\"", "").replace("'", "");
	try {
	    if (word.trim().length() > 0) {
		String payload = "submith=ok&text1="
		    +URLEncoder.encode(word, "windows-1256")
		    +"&submit1=Search&r1=p2e";

		Document doc = makeRequest(link, payload);

		// get definitions
		for (Element element : doc.getElementsByAttribute("onmouseover")) {		
		    definitions.add(element.text().trim());
		}
	    }
	} catch (SocketTimeoutException ste) {
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

	    return result;
	}
    }
}
