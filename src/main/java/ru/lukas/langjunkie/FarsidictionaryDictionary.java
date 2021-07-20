package ru.lukas.langjunkie;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class FarsidictionaryDictionary extends Dictionary {

    private static FarsidictionaryDictionary instance;

    private FarsidictionaryDictionary() {
	language = "faen";
	link = "https://www.farsidictionary.net";
	dictionaryName = "farsidictionary";
    }

    public static FarsidictionaryDictionary getInstance() {
	if (instance == null) {
	    instance = new FarsidictionaryDictionary();
	}

	return instance;
    }

    public LinkedHashMap search(String word) {
	Element table;
	Elements elems;
	LinkedHashMap result = new LinkedHashMap();
	ArrayList<String> resultSet = new ArrayList<String>();

	result.put("language", language);
	result.put("name", dictionaryName);
	result.put("link", link);
	result.put("searched_word", word);
	    
	try {
	    Document doc = makeRequest(link+"/index.php?q="+ word);

	    table = doc.getElementById("faen");
	    elems = table.getElementsByAttributeValue("align", "left");
	    elems.remove(0);
	    elems.remove(0);
	    
	    for (Element element : elems) {
		String text = element.text().trim();

		resultSet.add(text);
	    }

	} catch (SocketTimeoutException ste) {
	    // TODO: log the exception	    	    
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    result.put("results", resultSet);
	    result.put("examples", new ArrayList<String>());
	    result.put("synonyms", new ArrayList<String>());	    

	    return result;
	}
    }
    
}
