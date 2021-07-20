package ru.lukas.langjunkie;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class FarsidicDictionary extends Dictionary {

    private static FarsidicDictionary instance;

    private FarsidicDictionary() {
	language = "faen";
	link = "http://www.farsidic.com/en/Lang/FaEn";
	dictionaryName = "farsidic";
    }

    public static FarsidicDictionary getInstance() {
	if (instance == null) {
	    instance = new FarsidicDictionary();
	}

	return instance;
    }

    public LinkedHashMap search(String word) {
	Elements elems;
	LinkedHashMap result = new LinkedHashMap();
	ArrayList<String> resultSet = new ArrayList<String>();

	result.put("language", language);
	result.put("name", dictionaryName);
	result.put("link", link);
	result.put("searched_word", word);
	    
	try {
	    String payload = "SearchWord="
		+URLEncoder.encode(word, StandardCharsets.UTF_8.toString())
		+"&Criteria=Exact&ShowKeyboard=false";

	    Document doc = makeRequest(link, payload);

	    elems = doc.getElementsByClass("farsi-mean");
	    
	    for (Element element : elems) {		
		String splitted[] = element.text().split("\\,");

		for (String splEl : splitted) {
		    String trimmed = splEl.trim();
		    if (!resultSet.contains(trimmed)) {
			resultSet.add(trimmed);
		    }
		}
	    }
	    
	} catch (SocketTimeoutException ste) {
	    // TODO: log the exception	    
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    result.put("results", resultSet);
	    result.put("examples", new ArrayList<String>());
	    result.put("synonyms", new ArrayList<String>());	    	    

	    return result;
	}
    }
    
}
