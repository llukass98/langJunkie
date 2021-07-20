package ru.lukas.langjunkie;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class AbadisDictionary extends Dictionary {

    private static AbadisDictionary instance;

    private AbadisDictionary() {
	language = "faen";
	link = "https://abadis.ir";
	dictionaryName = "abadis";
    }

    public static AbadisDictionary getInstance() {
	if (instance == null) {
	    instance = new AbadisDictionary();
	}

	return instance;
    }

    public LinkedHashMap search(String word) {
	LinkedHashMap result = new LinkedHashMap();
	ArrayList<String> resultSet = new ArrayList<String>();
	ArrayList<String> examplesSet = new ArrayList<String>();	
	ArrayList<String> synonymsSet = new ArrayList<String>();	

	result.put("language", language);
	result.put("name", dictionaryName);
	result.put("link", link);
	result.put("searched_word", word);    

	try {
	    Document doc = makeRequest(link+"/?lntype=fatoen&word="+word);
	    Elements elems, examples;
	    Element definitions, synonyms;	    

	    // get definitions
	    definitions = doc.getElementById("Means");
	    elems = definitions.getElementsByClass("NoLinkColor");
	    
	    for (Element element : elems) {
		resultSet.add(element.text());
	    }

	    // get examples
	    examples = doc.getElementsByClass("Lun");

	    for (Element example : examples) {
		elems = example.getElementsByClass("WordB");

		if (elems != null) { 
		    for (Element elem : elems) {
			examplesSet.add(example.text().trim());
		    }
		}
		    
	    }
	    
	    // get synonyms
	    synonyms = doc.getElementById("FaToEnSyn");
	    elems = synonyms.getElementsByClass("Mean");

	    for (Element element : elems) {		
		for (String syn : element.text().split("\\,")) {
		    synonymsSet.add(syn.trim());
		}
	    }
	    
	} catch (SocketTimeoutException ste) {
	    // TODO: log the exception
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    result.put("results", resultSet);
	    result.put("examples", examplesSet);
	    result.put("synonyms", synonymsSet);	    

	    return result;
	}
    }
    
}
