package ru.lukas.langjunkie;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class BAmoozDictionary extends Dictionary {

    private static BAmoozDictionary instance;

    private BAmoozDictionary() {
	language = "faen";
	link = "https://dic.b-amooz.com";
	dictionaryName = "b-amooz";
    }

    public static BAmoozDictionary getInstance() {
	if (instance == null) {
	    instance = new BAmoozDictionary();
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
	    Document doc = makeRequest(link+"/en/dictionary/rw?word="+ word);

	    elems = doc.getElementsByClass("chip");
	    
	    for (Element element : elems) {
		resultSet.add(element.text().split("\\.")[1]);
	    }

	} catch (SocketTimeoutException | HttpStatusException ste) {
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
