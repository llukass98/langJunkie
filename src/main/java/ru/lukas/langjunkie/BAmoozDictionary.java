package ru.lukas.langjunkie;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class BAmoozDictionary extends Dictionary {

    public BAmoozDictionary() {
	language       = "faen";
	link           = "https://dic.b-amooz.com";
	dictionaryName = "b-amooz";
    }

    public HashMap search(String word) {
	HashMap result = new HashMap();
	ArrayList<String> definitions = new ArrayList<String>();
	
	try {
	    Document doc = makeRequest(link+"/en/dictionary/rw?word="+ word);
	    
	    for (Element element : doc.getElementsByClass("chip")) {
		definitions.add(element.text().split("\\.")[1]);
	    }

	} catch (SocketTimeoutException | HttpStatusException ste) {
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
