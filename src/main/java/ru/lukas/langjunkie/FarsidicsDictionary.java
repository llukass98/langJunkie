package ru.lukas.langjunkie;

import java.util.HashMap;
import java.util.ArrayList;
import java.net.SocketTimeoutException;
import org.jsoup.nodes.Document;

public class FarsidicsDictionary extends Dictionary {
    public FarsidicsDictionary() {
	language        = "faen";
	link            = "http://www.farsidics.com";
	dictionaryName  = "farsidics";
    }

    public HashMap search(String word) {
	HashMap result                = new HashMap();
	ArrayList<String> definitions = new ArrayList<String>();
        String blockOfDefinitions[]   = new String[50];

	word = sanitizeInput(word);
	try {
	    if (word.trim().length() > 0) {
		Document doc = makeRequest(link+"/ajax-searchf.php?keyword="+word);
		String text  = doc.body().text();

		if (!text.contains("No Results")) {
		    blockOfDefinitions = text.split(" ");
		    
		    for (int i = 1; i < blockOfDefinitions.length-1; i++) {
			if (!blockOfDefinitions[i].contains(",")) {
			    definitions.add(blockOfDefinitions[i].trim());	
			    break;
			}

			definitions.add(blockOfDefinitions[i].trim().replace(",", ""));
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
