package ru.lukas.langjunkie;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class AbadisDictionary extends Dictionary {

    public AbadisDictionary() {
	language       = "faen";
	link           = "https://abadis.ir";
	dictionaryName = "abadis";
    }

    public HashMap search(String word) {
	HashMap result = new HashMap();
	ArrayList<String> definitions = new ArrayList<String>();
	ArrayList<String> examples    = new ArrayList<String>();	
	ArrayList<String> synonyms    = new ArrayList<String>();	

	try {
	    Document doc = makeRequest(link+"/?lntype=fatoen&word="+word);
	    // get definitions ==============
	    Element blockOfDefinitions = doc.getElementById("Means");;	    

	    for (Element element : blockOfDefinitions.getElementsByClass("NoLinkColor")) {
		String definition = element.text();

		if (definition.charAt(0) == '[') {
		    definitions.add(definition.split("]")[1].trim());
		} else {
		    definitions.add(definition.trim());
		}
	    }
	    // ==============================
	    examples = parseExamples(doc);
	    synonyms = parseSynonyms(doc);
	    
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
	    result.put("examples",      examples);
	    result.put("synonyms",      synonyms);	    

	    return result;
	}
    }

    @Override
    protected ArrayList<String> parseSynonyms(Document html) {
	ArrayList<String> result = new ArrayList<String>();

	try {
	    String searchedWord = html.getElementsByTag("h1").first().text();
	    Element block = html.getElementById("FaToEnSyn");

	    for (Element element : block.getElementsByClass("Mean")) {		
		for (String synonym : element.text().split("\\،")) {
		    if (!synonym.trim().equals(searchedWord)) result.add(synonym.trim());
		}
	    }
	    
	} catch (NullPointerException e) {
	    e.printStackTrace();
	} finally {
	    return result;
	}
    }

    @Override
    protected ArrayList<String> parseExamples(Document html) {
	ArrayList<String> result = new ArrayList<String>();

	try {
	    for (Element element : html.getElementsByClass("Lun")) {
		for (Element example : element.getElementsByClass("WordB")) {
		    result.add(example.ownText().trim());
		}
	    }
	    
	} catch (NullPointerException e) {
	    e.printStackTrace();
	} finally {
	    return result;
	}	    
    }
    
}
