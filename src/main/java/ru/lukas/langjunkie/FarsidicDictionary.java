package ru.lukas.langjunkie;

import java.util.HashMap;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FarsidicDictionary extends Dictionary {
    public FarsidicDictionary() {
	language       = "faen";
	link           = "http://www.farsidic.com/en/Lang/FaEn";
	dictionaryName = "farsidic";
    }

    public HashMap search(String word) {
	HashMap result = new HashMap();
	ArrayList<String> definitions = new ArrayList<String>();

	word = sanitizeInput(word);
	try {
	    if (word.trim().length() > 0) {
		String payload = "SearchWord="
		    +URLEncoder.encode(word, StandardCharsets.UTF_8.toString())
		    +"&Criteria=Exact&ShowKeyboard=false";
	    
		Document doc = makeRequest(link, payload);
		// get definitions
		for (Element block : doc.getElementsByClass("farsi-mean")) {
		    for (String element : block.text().split("\\,")) {
			String trimmed = element.trim();
			
			if (!definitions.contains(trimmed)) {
			    definitions.add(trimmed.charAt(0) == '[' ?
					    trimmed.split("]")[1].trim() :
					    trimmed);
			}
		    }
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
