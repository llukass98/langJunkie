package ru.lukas.langjunkie.dictionarycollection.faen;

import java.util.HashMap;
import java.util.ArrayList;
import java.net.SocketTimeoutException;
import org.jsoup.HttpStatusException;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import ru.lukas.langjunkie.dictionarycollection.dictionary.Dictionary;

public class FarsidicsDictionary extends Dictionary {
    public FarsidicsDictionary() {
	language        = "faen";
	link            = "http://www.farsidics.com";
	dictionaryName  = "farsidics";
    }

    public HashMap search(String word) {
	HashMap result                = new HashMap();
	ArrayList<String> definitions = new ArrayList<String>();
        String[] blockOfDefinitions   = new String[100];

	try {	    
	    word = sanitizeInput(word);
	    Document doc = makeRequest(link+"/ajax-searchf.php?keyword="+word);
	    String text  = doc.body().text().replace(" _", "");

	    if (!text.contains("No Results")) {
		blockOfDefinitions = text.split(" ");
		    
		for (int i = 1; i < blockOfDefinitions.length-1; i++) {
		    if (blockOfDefinitions[i].contains("(")) { continue; } // skip trash data, if any
		    if (definitions.contains(blockOfDefinitions[i].trim().replace(",", ""))) { continue; } // skip duplicates
		    if (!blockOfDefinitions[i].contains(",")) { // find the last element and break the loop
			definitions.add(blockOfDefinitions[i].trim());	
			break;
		    }
		    definitions.add(blockOfDefinitions[i].trim().replace(",", ""));
		}
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
	    
	    return result;
	}
    }
}
