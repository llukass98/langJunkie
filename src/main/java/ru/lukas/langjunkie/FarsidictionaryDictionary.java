package ru.lukas.langjunkie;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class FarsidictionaryDictionary extends Dictionary {
    public FarsidictionaryDictionary() {
	language       = "faen";
	link           = "https://www.farsidictionary.net";
	dictionaryName = "farsidictionary";
    }

    public HashMap search(String word) {
	HashMap result = new HashMap();
	ArrayList<String> definitions = new ArrayList<String>();
	
	try {
	    Document doc   = makeRequest(link+"/index.php?q="+ word);
	    Elements elems = doc.getElementById("faen")
		            .getElementsByAttributeValue("align", "left");

	    elems.remove(0);
	    elems.remove(0);
	    
	    for (Element element : elems) {
		definitions.add(element.text().trim());
	    }
	} catch (SocketTimeoutException ste) {
	    // TODO: log the exception	    	    
	} catch (IOException e) {
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
